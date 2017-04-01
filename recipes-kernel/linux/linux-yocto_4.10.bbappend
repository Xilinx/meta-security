FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.10:"

# TPM kernel support
KERNEL_FEATURES_append += "${@bb.utils.contains('DISTRO_FEATURES', 'tpm', ' features/tpm/tpm.scc', '', d)}"

SRC_URI += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'apparmor', ' file://apparmor.cfg', '', d)} \
"

SRC_URI += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'smack', ' file://smack.cfg', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'smack', ' file://smack-default-lsm.cfg', '', d)} \
"
