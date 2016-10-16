FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.8:"

# TPM kernel support
SRC_URI += "\
        ${@bb.utils.contains('DISTRO_FEATURES', 'tpm', ' file://tpm.cfg', '', d)} \
        ${@bb.utils.contains('DISTRO_FEATURES', 'tpm', ' file://tpm.scc', '', d)} \
"
