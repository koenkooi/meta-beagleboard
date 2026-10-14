# wic can't set the MBR CHS geometry (parted reports 4 heads and 32 sectors for an
# image file), so patch the finished image. This runs inside IMAGE_CMD:wic, before the
# bmap and xz conversions, which a postfunc of do_image_wic would be too late for.
OMAP3_FIX_MBR_CHS = "${LAYERDIR_meta-beagleboard}/files/omap3-fix-mbr-chs.py"
do_image_wic[file-checksums] += "${OMAP3_FIX_MBR_CHS}:True"

IMAGE_CMD:wic:append() {
	# bbnote, because the script's own stdout didn't make it into the task log
	msg=$(python3 -I ${OMAP3_FIX_MBR_CHS} --write ${IMGDEPLOYDIR}/${IMAGE_NAME}.wic 2>&1) || bbfatal "omap3-fix-mbr-chs.py failed: $msg"
	bbnote "$msg"
}
