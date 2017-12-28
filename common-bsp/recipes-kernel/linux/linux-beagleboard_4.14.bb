require linux.inc

DESCRIPTION = "Linux kernel"
KERNEL_IMAGETYPE ?= "zImage"

COMPATIBLE_MACHINE = "(beaglebone)"

FILESPATH =. "${FILE_DIRNAME}/linux-beagleboard-4.14:${FILE_DIRNAME}/linux-beagleboard-4.14/${MACHINE}:"

S = "${WORKDIR}/git"

PV = "4.14.8"

SRC_URI = "git://github.com/beagleboard/linux.git;branch=4.14;nobranch=1"
SRCREV_pn-${PN} = "c588f00eecccb170ee18588058f41388862c1c56"

SRC_URI += " \
	file://defconfig \
        file://ccs.patch \
"

KERNEL_DEVICETREE = "am335x-bone.dtb am335x-boneblack.dtb am335x-bonegreen.dtb am335x-pocketbeagle.dtb"


PACKAGES =+ "kernel-dbg"
FILES_kernel-dbg += "/usr/src/kernel/firmware/.debug/"
