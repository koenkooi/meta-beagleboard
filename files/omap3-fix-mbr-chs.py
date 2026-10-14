#!/usr/bin/env python3
"""Rewrite the CHS fields of the MBR partition entries to a 255 head / 63 sector geometry.

wic gets its CHS values from parted, which describes an image file as 4 heads and
32 sectors, while the card images that boot on a BeagleBoard-xM (the Debian 9.5 one
was checked) and the old mkcard.sh use 255/63. The OMAP3 boot ROM documents the
partition start as derived from CHS, so make them consistent with the geometry the
ROM expects rather than find out which fields it reads.

Only the six CHS bytes of each used entry in sector 0 change. Last line is always
OMAP3-MBR-CHS: SUCCESS (...) or OMAP3-MBR-CHS: FAILURE (...), and a failure leaves the
image untouched.
"""
import os
import sys

HEADS, SPT = 255, 63
TAG = "OMAP3-MBR-CHS"


def fail(msg):
    print("%s: FAILURE (%s)" % (TAG, msg))
    sys.exit(1)


def chs(lba):
    cyl, rest = divmod(lba, HEADS * SPT)
    head, sec = divmod(rest, SPT)
    if cyl > 1023:
        return 1023, HEADS - 1, SPT
    return cyl, head, sec + 1


def pack(cyl, head, sec):
    return bytes([head, ((cyl >> 2) & 0xC0) | sec, cyl & 0xFF])


def main():
    if len(sys.argv) != 3 or sys.argv[1] != "--write":
        fail("usage: %s --write IMAGE" % sys.argv[0])
    try:
        fd = os.open(sys.argv[2], os.O_RDWR)
    except OSError as e:
        fail("cannot open %s: %s" % (sys.argv[2], e))
    old = os.pread(fd, 512, 0)
    if len(old) != 512 or old[510:512] != b"\x55\xaa":
        fail("no MBR signature in sector 0")
    new = bytearray(old)
    used = 0
    for i in range(4):
        off = 446 + 16 * i
        if old[off + 4] == 0:
            continue
        lba = int.from_bytes(old[off + 8:off + 12], "little")
        size = int.from_bytes(old[off + 12:off + 16], "little")
        if size == 0:
            fail("partition %d has zero size" % (i + 1))
        new[off + 1:off + 4] = pack(*chs(lba))
        new[off + 5:off + 8] = pack(*chs(lba + size - 1))
        used += 1
    if not used:
        fail("no used partition entries")
    os.pwrite(fd, bytes(new), 0)
    os.fsync(fd)
    if os.pread(fd, 512, 0) != bytes(new):
        fail("read back differs after write")
    print("%s: SUCCESS (%d partitions)" % (TAG, used))


if __name__ == "__main__":
    main()
