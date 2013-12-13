# Update DTC to latest git and apply DT overlay patch

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-dtc-Dynamic-symbols-fixup-support.patch"

