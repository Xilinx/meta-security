FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Tomoyo kernel support
SRC_URI += "${@base_contains('DISTRO_FEATURES', 'tomoyo', ' file://tomoyo.cfg', '', d)}"
