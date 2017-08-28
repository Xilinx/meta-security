FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.10:"

SRC_URI += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'apparmor', ' file://apparmor.cfg', '', d)} \
"

SRC_URI += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'smack', ' file://smack.cfg', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'smack', ' file://smack-default-lsm.cfg', '', d)} \
"
