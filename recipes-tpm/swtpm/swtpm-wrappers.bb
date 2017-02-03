SUMMARY = "SWTPM - OpenEmbedded wrapper scripts for native swtpm tools"
LICENSE = "MIT"
DEPENDS = "swtpm-native tpm-tools-native"

inherit native

# The whole point of the recipe is to make files available
# for use after the build is done, so don't clean up...
RM_WORK_EXCLUDE += "${PN}"

do_create_wrapper () {
    cat >${WORKDIR}/swtpm_setup_oe.sh <<EOF
#! /bin/sh
#
# Wrapper around swtpm_setup.sh which adds parameters required to
# run the setup as non-root directly from the native sysroot.

PATH="${bindir}:${base_bindir}:${sbindir}:${base_sbindir}:\$PATH"
export PATH

# tcsd only allows to be run as root or tss. Pretend to be root...
exec env ${FAKEROOTENV} ${FAKEROOTCMD} swtpm_setup.sh --config ${STAGING_DIR_NATIVE}/etc/swtpm_setup.conf "\$@"
EOF

    cat >${WORKDIR}/swtpm_cuse_oe.sh <<EOF
#! /bin/sh
#
# Wrapper around swtpm_cuse which makes it easier to invoke
# the right binary. Has to be run as root with TPM_PATH set
# to a directory initialized as virtual TPM by swtpm_setup_oe.sh.

PATH="${bindir}:${base_bindir}:${sbindir}:${base_sbindir}:\$PATH"
export PATH

exec swtpm_cuse "\$@"
EOF

    chmod a+rx ${WORKDIR}/*.sh
}

addtask do_create_wrapper before do_build after do_prepare_recipe_sysroot
