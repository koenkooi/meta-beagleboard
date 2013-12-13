# Update DTC to latest git and apply DT overlay patch

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRCREV = "65cc4d2748a2c2e6f27f1cf39e07a5dbabd80ebf"

SRC_URI += "file://0001-dtc-Dynamic-symbols-fixup-support.patch"

