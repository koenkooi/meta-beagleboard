DESCRIPTION = "Device Tree Overlays for bb.org boards"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

COMPATIBLE_MACHINE = "(beaglebone)"

PV = "1.0+git${SRCPV}"

DEPENDS = "dtc-native"

SRC_URI = "git://github.com/beagleboard/bb.org-overlays.git;branch=master;nobranch=1"
SRCREV_pn-${PN} = "25cf610eda61a323048bd46a458fe8bf6d1c6af5"

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
