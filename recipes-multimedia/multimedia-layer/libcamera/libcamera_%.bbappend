# BeagleBone camera cape (MT9M114 behind the CSSP bridge): the patches are
# made against libcamera master after v0.7.2 (a92285e), which is what was
# tested on the board. They do not apply to the recipe's v0.6.0, so the
# source is replaced completely for the beaglebone machines. This also drops
# tag=${PV} and the recipe's clang patch, neither of which fits this commit.
#
# Only parsed when meta-multimedia (meta-openembedded) is present, see
# BBFILES_DYNAMIC in conf/layer.conf.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:beaglebone = " \
    git://git.libcamera.org/libcamera/libcamera.git;protocol=https;branch=master \
    file://0001-pipeline-simple-add-the-BeagleBone-camera-cape-CSSP-.patch \
    file://0002-sensor-properties-mt9m114-initialise-unitCellSize-ex.patch \
    file://0003-sensor-legacy-support-image-processing-units-such-as.patch \
    file://0004-sensor-legacy-read-the-IFP-selection-rectangles-from.patch \
    file://0005-pipeline-simple-only-delay-sensor-controls-that-exis.patch \
"
SRCREV:beaglebone = "a92285e9c8f8d5aba24c6f72f49efc73074002e1"
PV:beaglebone = "0.7.2+git"

PACKAGE_ARCH:beaglebone = "${MACHINE_ARCH}"

# The MT9M114 IFP outputs YUV/RGB, so the simple pipeline without soft ISP
LIBCAMERA_PIPELINES:beaglebone = "simple"

PACKAGECONFIG:append:beaglebone = " gst"

# v4l2 is a feature option here, 'true' is rejected. Everything that is
# 'auto' and not needed is disabled so the build cannot pick it up by chance
# (no GPU driver on the SGX530, so no GPU soft ISP).
EXTRA_OEMESON:remove:beaglebone = "-Dv4l2=true"
EXTRA_OEMESON:append:beaglebone = " \
    -Dv4l2=enabled \
    -Dudev=enabled \
    -Dsoftisp-gpu=disabled \
    -Drpi-awb-nn=disabled \
    -Dlibdw=disabled \
    -Dlibunwind=disabled \
    -Dtracing=disabled \
    -Dcam-jpeg=disabled \
    -Dcam-output-kms=disabled \
    -Dcam-output-sdl2=disabled \
"
