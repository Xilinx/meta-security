EXTRA_OECONF += "${@bb.utils.contains('DISTRO_FEATURES', 'tpm', '--enable-tpm', '', d)}"
