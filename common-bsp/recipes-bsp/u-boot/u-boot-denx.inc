DESCRIPTION = "U-Boot - the Universal Boot Loader"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"

PROVIDES = "virtual/bootloader"

DEPENDS = "mtd-utils"

PACKAGE_ARCH = "${MACHINE_ARCH}"
PARALLEL_MAKE=""

TOOLCHAIN_OPTIONS ?= ""

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

UBOOT_MACHINE ?= "${MACHINE}_config"
UBOOT_BINARY ?= "u-boot.bin"
UBOOT_IMAGE ?= "u-boot-${MACHINE}-${PV}-${PR}.bin"
UBOOT_SYMLINK ?= "u-boot-${MACHINE}.bin"
UBOOT_MAKE_TARGET ?= "all"

# Allow setting an additional version string that will be picked up by the
# u-boot build system and appended to the u-boot version.  If the .scmversion
# file already exists it will not be overwritten.
UBOOT_LOCALVERSION ?= ""


do_configure () {
	oe_runmake ${UBOOT_MACHINE}
}

do_compile () {
	if [ "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld$/$(CROSS_COMPILE)ld.bfd/g' config.mk
	fi

	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	if [ ! -e ${B}/.scmversion -a ! -e ${S}/.scmversion ] ; then
		echo ${UBOOT_LOCALVERSION} > ${B}/.scmversion
		echo ${UBOOT_LOCALVERSION} > ${S}/.scmversion
	fi

	oe_runmake ${UBOOT_MAKE_TARGET}
}

do_install () {
	install -d ${D}/boot
	install ${B}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
	ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}

	if [ -e ${B}/MLO ] ; then
		install ${B}/MLO ${D}/boot/MLO-${MACHINE}-${PV}
		ln -sf MLO-${MACHINE}-${PV} ${D}/boot/MLO
	fi

	if [ -e ${WORKDIR}/fw_env.config ] ; then
		install -d ${D}${sysconfdir}
		install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
	fi

}

FILES_${PN} = "/boot ${sysconfdir}"
# no gnu_hash in uboot.bin, by design, so skip QA
INSANE_SKIP_${PN} = "1"

inherit deploy

addtask deploy before do_package after do_compile

do_deploy () {
	install -d ${DEPLOYDIR}
	install ${B}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}

	cd ${DEPLOYDIR}
	rm -f ${UBOOT_BINARY} ${UBOOT_SYMLINK}
	ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}
	ln -sf ${UBOOT_IMAGE} ${UBOOT_BINARY}

	if [ -e ${B}/MLO ] ; then
		install ${B}/MLO ${DEPLOYDIR}/MLO-${MACHINE}-${PV}
		rm -f ${DEPLOYDIR}/MLO-${MACHINE} ${DEPLOYDIR}/MLO
		ln -sf MLO-${MACHINE}-${PV} ${DEPLOYDIR}/MLO-${MACHINE}
		ln -sf MLO-${MACHINE}-${PV} ${DEPLOYDIR}/MLO
	fi
}
