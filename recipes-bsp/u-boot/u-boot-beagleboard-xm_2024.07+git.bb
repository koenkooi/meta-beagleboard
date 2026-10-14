require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native gnutls-native python3-pyelftools-native"

# omap3_beagle was removed from upstream U-Boot (c5de07144a) because it was not
# converted to DM_I2C by the deadline. This is the parent of that removal, so the
# newest revision that can still build the BeagleBoard-xM. Pinned to a revision
# rather than the v2024.07 release because the release predates the DM_I2C
# conversion of board/beagle (3efd925d70) that U-Boot's own migration deadline
# asked for. It can't track the oe-core u-boot recipe, so it is only usable by
# the machine named below.
PV = "2024.07+git"
SRCREV = "26ed58b40f5866c016ea5bc4545ab54a755b419d"
SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master"

COMPATIBLE_MACHINE = "^beagleboard-xm$"

# OpenSSL 4 removed the engine API that lib/rsa/rsa-sign.c in this old U-Boot still
# uses, so mkimage (a host tool, built with BUILD_CFLAGS) fails to link. The stubs
# define the engine symbols away; the pinned U-Boot can't be moved to a release that
# dropped the engine code, because that release no longer builds the BeagleBoard-xM.
BUILD_CFLAGS += "-DOPENSSL_ENGINE_STUBS"
