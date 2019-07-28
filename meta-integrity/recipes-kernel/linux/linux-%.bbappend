FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'ima', ' file://ima.cfg', '', d)}"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'modsign', ' file://modsign.scc file://modsign.cfg', '', d)}"

inherit ${@bb.utils.contains('DISTRO_FEATURES', 'modsign', 'kernel-modsign', '', d)}
