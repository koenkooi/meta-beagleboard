require u-boot-denx.inc

# SPL build
UBOOT_BINARY = "u-boot.img"
UBOOT_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}.img"
UBOOT_SYMLINK = "u-boot-${MACHINE}.img"

PV = "2016.01"

# No patches for other machines yet
COMPATIBLE_MACHINE = "(beaglebone)"

# File is board-specific, only copy when it will be correct.
FWENV = ""

SRC_URI = "git://git.denx.de/u-boot.git \
           file://2013.10/0001-am335x-mux-don-t-hang-on-unknown-EEPROMs-assume-Beag.patch \
           file://2013.10/0003-beaglebone-HACK-raise-USB-current-limit.patch \
           file://2014.01/0002-beaglebone-HACK-always-return-1-for-is_bone_lt.patch \
           ${FWENV} \
          "

# v2016.01 release tag
SRCREV = "fa85e826c16b9ce1ad302a57e9c4b24db0d8b930"

LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=2687c5ebfd9cb284491c3204b726ea29"

S = "${WORKDIR}/git"

