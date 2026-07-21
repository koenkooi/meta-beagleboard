FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

PATCHTOOL = "git"

# linux-stable.inc sets S = "${WORKDIR}/git", which doesn't match where the git fetcher
# actually unpacks to (UNPACKDIR/BB_GIT_DEFAULT_DESTSUFFIX; see how meta-ti's kernel
# recipes do it). That mismatch makes do_kernel_checkout's S-vs-source_unpackdir
# reconciliation (rm -rf S; mv ... S) fire even though it isn't needed, and it runs
# after do_symlink_kernsrc has already turned S into a symlink into the shared
# STAGING_KERNEL_DIR (work-shared/beaglebone/kernel-source) -- clobbering it and leaving
# it empty for any out-of-tree kernel module build (make-mod-scripts fails with "No rule
# to make target 'prepare'"). Overriding S here to match the real unpack destination
# skips that branch entirely. Scoped to beaglebone only pending an upstream fix in
# meta-linux-mainline for the other machines/kernels that share this same bug.
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}"

FILES:${KERNEL_PACKAGE_NAME}-devicetree += " /boot/dtb"
SRC_URI:append = " file://makeitwork.cfg \
                   file://0001-iio-light-vcnl4000-initialize-ps_scale-for-vcnl4000-.patch;patch=1 \
                   file://0002-sched-allow-CAKE-to-be-set-as-default.patch \
                   file://0003-drm-ssd130x-Add-support-for-SINO-WEALTH-SH1107.patch \
                   file://0004-dt-bindings-display-ssd1307fb-Add-entry-for-SINO-WEA.patch \
                 "
