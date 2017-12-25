require u-boot-denx.inc

# SPL build
UBOOT_BINARY = "u-boot.img"
UBOOT_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}.img"
UBOOT_SYMLINK = "u-boot-${MACHINE}.img"

PV = "2018.01"

# No patches for other machines yet
COMPATIBLE_MACHINE = "(beaglebone|pocketbeagle|beaglebone-white|beaglebone-black)"

# File is board-specific, only copy when it will be correct.
FWENV = ""

SRC_URI = "git://git.denx.de/u-boot.git \
           file://0001-am335x_evm-uEnv.txt-bootz-n-fixes.patch \
           ${FWENV} \
          "

SRCREV = "9da71fc83a38e9dbf71240b3e548f6b37417764a"

LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=2687c5ebfd9cb284491c3204b726ea29"

S = "${WORKDIR}/git"

