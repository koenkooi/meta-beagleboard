DESCRIPTION = "Extended task to get System Test specific apps"
LICENSE = "MIT"

inherit packagegroup

TEST = "\
    bonnie++ \
    hdparm \
    iozone3 \
    iperf \
    lmbench \
    rt-tests \
    evtest \
    bc \
    memtester \
    "

RDEPENDS_${PN} = "\
    ${TEST} \
    "

