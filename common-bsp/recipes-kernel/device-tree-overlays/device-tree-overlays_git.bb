DESCRIPTION = "Device Tree Overlays for bb.org boards"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

COMPATIBLE_MACHINE = "(beaglebone)"

PV = "1.0+git${SRCPV}"

DEPENDS = "dtc-native"

SRC_URI = "git://github.com/beagleboard/bb.org-overlays.git;branch=master;nobranch=1"
SRCREV_pn-${PN} = "d4b0ba69d7ba6cfb80d5288faea2991af7dfff6f"

S = "${WORKDIR}/git"

do_compile() {
	oe_runmake DTC=dtc
}

do_install() {
	oe_runmake install DESTDIR=${D}
}


FILES_${PN} = " \
	/lib/firmware \
"
