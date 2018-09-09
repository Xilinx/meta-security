SUMMARY = "Software stack for TPM2."
DESCRIPTION = "tpm2.0-tss like woah."
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0b1d631c4218b72f6b05cb58613606f4"
SECTION = "tpm"

DEPENDS = "autoconf-archive-native libgcrypt"

SRCREV = "dc31e8dca9dbc77d16e419dc514ce8c526cd3351"

SRC_URI = "git://github.com/tpm2-software/tpm2-tss.git;branch=2.0.x"

inherit autotools-brokensep pkgconfig systemd

S = "${WORKDIR}/git"

do_configure_prepend () {
       ./bootstrap
}

INHERIT += "extrausers"
EXTRA_USERS_PARAMS = "\
	useradd -p '' tss; \
	groupadd tss; \
	"

SYSTEMD_PACKAGES = "resourcemgr"
SYSTEMD_SERVICE_resourcemgr = "resourcemgr.service"
SYSTEMD_AUTO_ENABLE_resourcemgr = "enable"

do_patch[postfuncs] += "${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','systemd','fix_systemd_unit','', d)}"
fix_systemd_unit () {
    sed -i -e 's;^ExecStart=.*/resourcemgr;ExecStart=${sbindir}/resourcemgr;' ${S}/contrib/resourcemgr.service
}

do_install_append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m0644 ${S}/contrib/resourcemgr.service ${D}${systemd_system_unitdir}/resourcemgr.service
    fi
}

PROVIDES = "${PACKAGES}"
PACKAGES = " \
    ${PN} \
    ${PN}-dbg \
    ${PN}-doc \
    libtss2-mu \
    libtss2-mu-dev \
    libtss2-mu-staticdev \
    libtss2-tcti-device \
    libtss2-tcti-device-dev \
    libtss2-tcti-device-staticdev \
    libtss2-tcti-mssim \
    libtss2-tcti-mssim-dev \
    libtss2-tcti-mssim-staticdev \
    libtss2 \
    libtss2-dev \
    libtss2-staticdev \
    resourcemgr \
"

FILES_libtss2-tcti-device = "${libdir}/libtss2-tcti-device.so.*"
FILES_libtss2-tcti-device-dev = " \
    ${includedir}/tss2/tss2_tcti_device.h \
    ${libdir}/pkgconfig/tss2-tcti-device.pc \
    ${libdir}/libtss2-tcti-device.so"
FILES_libtss2-tcti-device-staticdev = "${libdir}/libtss2-tcti-device.*a"

FILES_libtss2-tcti-mssim = "${libdir}/libtss2-tcti-mssim.so.*"
FILES_libtss2-tcti-mssim-dev = " \
    ${includedir}/tss2/tss2_tcti_mssim.h \
    ${libdir}/pkgconfig/tss2-tcti-mssim.pc \
    ${libdir}/libtss2-tcti-mssim.so"
FILES_libtss2-tcti-mssim-staticdev = "${libdir}/libtss2-tcti-mssim.*a"

FILES_libtss2-mu = "${libdir}/libtss2-mu.so.*"
FILES_libtss2-mu-dev = " \
    ${includedir}/tss2/tss2_mu.h \
    ${libdir}/pkgconfig/tss2-mu.pc \
    ${libdir}/libtss2-mu.so"
FILES_libtss2-mu-staticdev = "${libdir}/libtss2-mu.*a"

FILES_libtss2 = "${libdir}/libtss2*so.*"
FILES_libtss2-dev = " \
    ${includedir} \
    ${libdir}/pkgconfig \
    ${libdir}/libtss2*so"
FILES_libtss2-staticdev = "${libdir}/libtss*a"

FILES_${PN} = "${libdir}/udev"

FILES_resourcemgr = "${sbindir}/resourcemgr ${systemd_system_unitdir}/resourcemgr.service"
