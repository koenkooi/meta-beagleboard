require linux.inc

DESCRIPTION = "Linux kernel"
KERNEL_IMAGETYPE ?= "zImage"

COMPATIBLE_MACHINE = "(beaglebone)"

FILESPATH =. "${FILE_DIRNAME}/linux-beagleboard-4.1:${FILE_DIRNAME}/linux-beagleboard-4.1/${MACHINE}:"

S = "${WORKDIR}/git"

PV = "4.1.30"

SRC_URI = "git://github.com/beagleboard/linux.git;branch=4.1;nobranch=1"
SRCREV_pn-${PN} = "bb1136e4c931dd48aa5671bbeb8c945fefdd6bc2"

SRC_URI += " \
        file://0001-mt7601u-fix-dma-from-stack-address.patch \
        file://0002-mt7601u-use-correct-ieee80211_rx-variant.patch \
        file://0003-mt7601u-fix-tx-status-reporting-contexts.patch \
        file://0004-mt7601u-lock-out-rx-path-and-tx-status-reporting.patch \
	file://defconfig \
	file://logo_linux_clut224.ppm \
"

KERNEL_DEVICETREE = "am335x-bone.dtb am335x-boneblack.dtb am335x-bonegreen.dtb"

KERNEL_CC_append = " -fuse-ld=bfd"

PACKAGES =+ "kernel-dbg"
FILES_kernel-dbg += "/usr/src/kernel/firmware/.debug/"
