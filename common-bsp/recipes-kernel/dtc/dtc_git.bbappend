# Update DTC to use Pantelis Antoniou's (@pantoniou) DT overlay patch

SRC_URI_remove = "git://git.kernel.org/pub/scm/utils/dtc/dtc.git"

SRC_URI += "git://github.com/pantoniou/dtc.git;branch=dt-overlays8"

SRCREV = "f7da040f2bed614fd55a4901d71fafb916863e8a"

PV = "1.4.1+git${SRCPV}"
