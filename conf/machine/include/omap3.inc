require conf/machine/include/ti-soc.inc
SOC_FAMILY:append = ":omap3"

DEFAULTTUNE ?= "armv7athf-neon"
require conf/machine/include/arm/armv7a/tune-cortexa8.inc

KERNEL_IMAGETYPE = "zImage"
KBUILD_DEFCONFIG = "omap2plus_defconfig"
PREFERRED_PROVIDER_virtual/kernel ?= "linux-stable"

# Machines list their DTBs explicitly, an empty prefix keeps meta-ti from globbing the whole ti/omap directory
KERNEL_DEVICETREE_PREFIX = ""

UBOOT_ARCH = "arm"
UBOOT_ENTRYPOINT = "0x80008000"
UBOOT_LOADADDRESS = "0x80008000"

# The boot ROM loads MLO (SPL) from the first FAT partition and SPL then loads
# u-boot.img, which finds extlinux.conf through distro boot
SPL_BINARY = "MLO"
UBOOT_SUFFIX = "img"
EXTRA_IMAGEDEPENDS += "virtual/bootloader"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-image-zimage"

IMAGE_FSTYPES += "tar.xz wic.xz wic.bmap"
# meta-ti's sdimage-2part.wks gives a FAT32 partition typed 0x0c and the wrong MBR geometry
WKS_FILE ?= "omap3-sdimage-2part.wks.in"

IMAGE_CLASSES += "omap3-sdcard"
# extlinux/extlinux.conf is written by wic, see the wks
IMAGE_BOOT_FILES += "${SPL_BINARY} u-boot.${UBOOT_SUFFIX} ${KERNEL_IMAGETYPE} ${DEVICETREE_FILES}"
do_image_wic[depends] += "virtual/bootloader:do_deploy"
