#
# TPM kernel support

KERNEL_FEATURES_append += "${@bb.utils.contains('MACHINE_FEATURES', 'tpm', ' features/tpm/tpm.scc', '', d)}"

