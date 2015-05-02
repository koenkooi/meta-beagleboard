require linux.inc

DESCRIPTION = "Linux kernel"
KERNEL_IMAGETYPE ?= "zImage"

COMPATIBLE_MACHINE = "(beaglebone)"

FILESPATH =. "${FILE_DIRNAME}/linux-beagleboard-3.8:${FILE_DIRNAME}/linux-beagleboard-3.8/${MACHINE}:"

S = "${WORKDIR}/git"

PV = "3.8.13"
PR = "r27"

SRC_URI = "git://github.com/beagleboard/linux.git;branch=3.8;nobranch=1"
SRCREV_pn-${PN} = "205cbd63c832ed34695d94985f5a404d37a18546"

do_configure_prepend() {
	if [ -e ${WORKDIR}/am335x-pm-firmware.bin ] ; then
		cp ${WORKDIR}/am335x-pm-firmware.bin ${S}/firmware
	fi

	if [ -e ${WORKDIR}/merge_config.sh ] ; then
                cp ${WORKDIR}/merge_config.sh ${S}/scripts/kconfig
        fi
}

SRC_URI += " \
	file://btrfs/0001-decompressor-add-LZ4-decompressor-module.patch \
	file://btrfs/0002-lib-add-support-for-LZ4-compressed-kernel.patch \
	file://btrfs/0003-lib-add-lz4-compressor-module.patch \
	file://btrfs/0004-lib-lz4-correct-the-LZ4-license.patch \
	file://btrfs/0005-lz4-fix-compression-decompression-signedness-mismatc.patch \
	file://btrfs/0006-btrfs-prepare-incompat-flags-for-more-compression-me.patch \
	file://btrfs/0007-btrfs-lz4-add-wrapper-functions-and-enable-it.patch \
	file://btrfs/0008-btrfs-add-lz4hc-incompat-bits.patch \
	file://btrfs/0009-btrfs-add-lz4hc-wrapper-and-enable-it.patch \
	file://btrfs/0010-btrfs-reduce-duplicate-code-in-lz4_wrapper.c.patch \
	file://btrfs/0011-btrfs-select-LZ4-HC-libs.patch \
	file://0001-net-add-proper-db.txt.patch \
	file://0001-kernel-add-support-for-gcc-5.patch \
	file://0001-ARM-8158-1-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
	file://0001-Input-lifebook-use-static-inline-instead-of-inline-i.patch \
	file://0001-Input-sentelic-use-static-inline-instead-of-inline.patch \
	file://0001-Staging-rtl8188eu-Remove-extern-as-it-is-defined-but.patch \
	file://0001-Fix-extern-inline-use-for-gcc-4.3-in-c99-mode.patch \
	file://defconfig \
	file://am335x-pm-firmware.bin \
	file://logo_linux_clut224.ppm \
	file://merge_config.sh \
"
