require linux.inc

DESCRIPTION = "Linux kernel"
KERNEL_IMAGETYPE ?= "zImage"

COMPATIBLE_MACHINE = "(beaglebone)"

FILESPATH =. "${FILE_DIRNAME}/linux-beagleboard-4.1:${FILE_DIRNAME}/linux-beagleboard-4.1/${MACHINE}:"

S = "${WORKDIR}/git"

PV = "4.1.15"

SRC_URI = "git://github.com/beagleboard/linux.git;branch=4.1;nobranch=1"
SRCREV_pn-${PN} = "dd387851fb2923733ba15708dc8d812e71533a80"

do_configure_prepend() {
	if [ -e ${WORKDIR}/am335x-pm-firmware.bin ] ; then
		cp ${WORKDIR}/am335x-pm-firmware.bin ${S}/firmware
	fi
}

SRC_URI += " \
	file://defconfig \
	file://logo_linux_clut224.ppm \
"

KERNEL_DEVICETREE = "am335x-bone.dtb am335x-boneblack.dtb am335x-bonegreen.dtb"

KERNEL_CC_append = " -fuse-ld=bfd"


PACKAGES =+ "kernel-dbg"
FILES_kernel-dbg += "/usr/src/kernel/firmware/.debug/"
