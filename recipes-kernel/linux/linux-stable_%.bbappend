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
                                  file://0008-ARM-dts-omap3-beagle-xm-add-BeagleBoardToys-WiFi-ove.patch \
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

# --- BeagleBone camera cape series (QuickLogic CSSP + Aptina MT9M114/MT9T112) ---
# Patches 0009-0023 are the camera-cape-overlays branch of the camera-cape linux-stable
# tree (0009-0021 are camera-cape-7.2.6) on top of v7.2.6 + 0001-0004: vendor prefix and bindings, mt9t112 OF/media
# controller support, the EDMA source port window (dmaengine core + ti/edma), the
# CSSP camera interface driver, mt9m114 fixes for the RAW10 bypass path, and the
# cape overlays named after the cape EEPROM (0022 BB-BONE-CAM-VVDN-00A0, 0023
# BB-BONE-CAM3-01-00A2; the latter is untested on hardware). The overlays are built by
# kbuild and listed in KERNEL_DEVICETREE in conf/machine/beaglebone.conf. Scoped to
# beaglebone because 0014/0015 change built-in code that the xM's omap2plus kernel also builds; the xM source tree must stay
# unchanged. Drop each patch once the recipe moves to a kernel that contains it.
SRC_URI:append:beaglebone = " file://0009-dt-bindings-vendor-prefixes-Add-QuickLogic.patch \
                              file://0010-dt-bindings-media-Add-QuickLogic-CSSP-camera-interfa.patch \
                              file://0011-dt-bindings-media-i2c-Add-Aptina-MT9T111-MT9T112.patch \
                              file://0012-media-i2c-mt9t112-Add-device-tree-and-media-controll.patch \
                              file://0013-media-platform-Add-QuickLogic-CSSP-camera-interface-.patch \
                              file://0014-dmaengine-Add-max_src_port_window_size-to-the-slave-.patch \
                              file://0015-dmaengine-ti-edma-Add-source-port-window-support-for.patch \
                              file://0016-media-quicklogic-cssp-camif-Use-the-DMA-port-window-.patch \
                              file://0017-media-i2c-mt9m114-Set-the-subdev-control-handler-bef.patch \
                              file://0018-media-i2c-mt9m114-Restrict-frame-interval-ioctls-to-.patch \
                              file://0019-media-i2c-mt9m114-Store-the-frame-interval-in-the-su.patch \
                              file://0020-media-i2c-mt9m114-Program-the-IFP-geometry-from-the-.patch \
                              file://0021-media-i2c-mt9m114-Fix-2x2-summing-in-RAW10-bypass.patch \
                              file://0022-ARM-dts-am335x-bone-add-the-BeagleBone-VVDN-camera-c.patch \
                              file://0023-ARM-dts-am335x-bone-add-the-BeagleBone-CAM3-camera-c.patch \
                              file://beaglebone-camera-cape.cfg \
                            "
# --- end of BeagleBone camera cape series ---
