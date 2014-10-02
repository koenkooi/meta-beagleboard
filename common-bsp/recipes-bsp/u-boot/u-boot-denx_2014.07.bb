require u-boot-denx.inc

# SPL build
UBOOT_BINARY = "u-boot.img"
UBOOT_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}.img"
UBOOT_SYMLINK = "u-boot-${MACHINE}.img"

PV = "2014.07"

# No patches for other machines yet
COMPATIBLE_MACHINE = "(beaglebone)"

# File is board-specific, only copy when it will be correct.
FWENV = ""

SRC_URI = "git://git.denx.de/u-boot.git \
           file://2013.10/0001-am335x-mux-don-t-hang-on-unknown-EEPROMs-assume-Beag.patch \
           file://2013.10/0003-beaglebone-HACK-raise-USB-current-limit.patch \
           file://2014.01/0002-beaglebone-HACK-always-return-1-for-is_bone_lt.patch \
           file://2014.01/0005-explicitly-clear-unaligned-access-bit.patch \
           file://2014.01/0009-ti_armv7_common.h-enable-CONFIG_API.patch \
           ${FWENV} \
          "

# v2014.07 release tag
SRCREV = "524123a70761110c5cf3ccc5f52f6d4da071b959"

LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=2687c5ebfd9cb284491c3204b726ea29"

S = "${WORKDIR}/git"

