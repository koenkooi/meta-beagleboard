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

SRC_URI:append:beagleboard-xm = " file://0005-ARM-dts-omap3-beagle-xm-add-CircuitCo-BBToys-uLCD7-.patch \
                                  file://beagleboard-xm.cfg \
                                "

# Linux 7.2 registers the TI dual-mode timer as a clocksource and clockevent from
# timer-ti-dm.c as well, which on OMAP3 collides with the early-boot registration in
# timer-ti-dm-systimer.c and hangs the boot right after "Switched to clocksource
# omap_dm_timer". This is the upstream fix (linux-next 0d3a67b1954a, Tested-by on a
# Beagle XM); drop it once the linux-stable recipe is on a release that has it. It
# only applies to 7.2.x, so it fails loudly if the recipe moves to an older kernel.
SRC_URI:append:omap3 = " file://0006-clocksource-drivers-timer-ti-dm-Register-clocksource-.patch"

# The NEON wakeup dependency is only added at the end of omap3_pm_init(), after the
# clockdomains were allowed to idle, so a task switch in between can read FPEXC while
# NEON is asleep and recurse in vfp_support_entry until the stack overflows. Seen on
# the BeagleBoard-xM in most boots right after the SWP emulation message; with this
# patch three boots in a row were clean.
SRC_URI:append:omap3 = " file://0007-ARM-OMAP2-omap3_pm_init-add-the-NEON-wakeup-dependency.patch"
