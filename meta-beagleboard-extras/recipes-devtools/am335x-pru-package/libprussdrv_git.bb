include am335x-pru-package.inc

SUMMARY = "libprussdrv - PRU driver library"
DESCRIPTION = "libprussdrv - driver library for programmable realtime unit sub system"
SECTION = "devel"

LICENSE = "TI"
LIC_FILES_CHKSUM = "file://prussdrv.c;beginline=6;endline=47;md5=b6e20afe744fe2321f58d3a4c7d06946"

S = "${WORKDIR}/git/pru_sw/app_loader/interface"

COMPATIBLE_MACHINE = "(beaglebone)"

CFLAGS += "-I. -Wall -I../include   -c -fPIC -O3 -mtune=cortex-a8 -march=armv7-a -shared"
SOURCES = "prussdrv.c"

do_compile () {
    ${CC} ${CFLAGS} ${SOURCES} -o prussdrv.o
    ${CC} -shared -o libprussdrv.so.1.0.0 prussdrv.o
}

do_install() {
    install -d ${D}${libdir}
    install -m 0755 ${S}/libprussdrv.so.1.0.0 ${D}${libdir}
    ln -s libprussdrv.so.1.0.0 ${D}${libdir}/libprussdrv.so.1
    ln -s libprussdrv.so.1.0.0 ${D}${libdir}/libprussdrv.so
}

FILES_${PN} = " \
               ${libdir} \
               ${libdir}/libprussdrv.so.${PV} \
               ${libdir}/libprussdrv.so \
"
