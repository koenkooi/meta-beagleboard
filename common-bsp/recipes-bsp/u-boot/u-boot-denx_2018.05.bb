require u-boot-denx.inc

# SPL build
UBOOT_BINARY = "u-boot.img"
UBOOT_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}.img"
UBOOT_SYMLINK = "u-boot-${MACHINE}.img"

PV = "2018.05"

# No patches for other machines yet
COMPATIBLE_MACHINE = "(beaglebone|beaglebone-white|beaglebone-black)"

# File is board-specific, only copy when it will be correct.
FWENV = ""

SRC_URI = "git://git.denx.de/u-boot.git \
           ${FWENV} \
          "

SRCREV = "890e79f2b1c26c5ba1a86d179706348aec7feef7"

LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=9915e8cb100eb5dbb366010a0f10296c"

S = "${WORKDIR}/git"

