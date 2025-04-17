FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://makeitwork.config;subdir=fragments"
KERNEL_CONFIG_FRAGMENTS:append = " ${WORKDIR}/sources-unpack/fragments/makeitwork.config"
