DESCRIPTION = "Device Tree Overlays for bb.org boards"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

COMPATIBLE_MACHINE = "(beaglebone)"

PV = "1.0+git${SRCPV}"

DEPENDS = "dtc-native"

SRC_URI = "git://github.com/beagleboard/bb.org-overlays.git;branch=master;nobranch=1"
SRCREV_pn-${PN} = "25cf610eda61a323048bd46a458fe8bf6d1c6af5"

S = "${WORKDIR}/git"

do_configure() {
	:
}

do_compile() {
	oe_runmake DTC=dtc
}

do_install() {
	oe_runmake install DESTDIR=${D}
}


FILES_${PN} = " \
	/lib/firmware \
"
