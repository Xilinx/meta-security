SUMMARY = "Software stack for TPM2."
DESCRIPTION = "tpm2.0-tss like woah."
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=17067aa50a585593d421b16cffd805a9"
SECTION = "tpm"

SRCREV = "8e25d0cbb287d30c93b2b77e99bc761dc67e31a9"
SRC_URI = " \
    git://github.com/01org/TPM2.0-TSS.git;protocol=git;branch=master;name=TPM2.0-TSS;destsuffix=TPM2.0-TSS \
    file://ax_pthread.m4 \
    file://fix_musl_select_include.patch "

inherit autotools pkgconfig systemd

S = "${WORKDIR}/${@d.getVar('BPN',d).upper()}"

do_configure_prepend () {
	mkdir -p ${S}/m4
	cp ${WORKDIR}/ax_pthread.m4 ${S}/m4
	# execute the bootstrap script
	currentdir=$(pwd)
	cd ${S}
	./bootstrap --force
	cd $currentdir
}

INHERIT += "extrausers"
EXTRA_USERS_PARAMS = "\
	useradd -p '' tss; \
	groupadd tss; \
	"

SYSTEMD_PACKAGES += "resourcemgr"
SYSTEMD_SERVICE_resourcemgr = "resourcemgr.service"
SYSTEMD_AUTO_ENABLE_resourcemgr = "enable"

do_patch[postfuncs] += "fix_systemd_unit"
fix_systemd_unit () {
    sed -i -e 's;^ExecStart=.*/resourcemgr;ExecStart=${sbindir}/resourcemgr;' ${S}/contrib/resourcemgr.service
}

do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m0644 ${S}/contrib/resourcemgr.service ${D}${systemd_system_unitdir}/resourcemgr.service
}

PROVIDES = "${PACKAGES}"
PACKAGES = " \
    ${PN}-dbg \
    libtss2 \
    libtss2-dev \
    libtss2-staticdev \
    libtctidevice \
    libtctidevice-dev \
    libtctidevice-staticdev \
    libtctisocket \
    libtctisocket-dev \
    libtctisocket-staticdev \
    resourcemgr \
"

FILES_libtss2 = "${libdir}/libsapi.so.0.0.0"
FILES_libtss2-dev = " \
    ${includedir}/sapi \
    ${includedir}/tcti/common.h \
    ${libdir}/libsapi.so* \
    ${libdir}/pkgconfig/sapi.pc \
"
FILES_libtss2-staticdev = " \
    ${libdir}/libsapi.a \
    ${libdir}/libsapi.la \
"
FILES_libtctidevice = "${libdir}/libtcti-device.so.0.0.0"
FILES_libtctidevice-dev = " \
    ${includedir}/tcti/tcti_device.h \
    ${libdir}/libtcti-device.so* \
    ${libdir}/pkgconfig/tcti-device.pc \
"
FILES_libtctidevice-staticdev = "${libdir}/libtcti-device.*a"
FILES_libtctisocket = "${libdir}/libtcti-socket.so.0.0.0"
FILES_libtctisocket-dev = " \
    ${includedir}/tcti/tcti_socket.h \
    ${libdir}/libtcti-socket.so* \
    ${libdir}/pkgconfig/tcti-socket.pc \
"
FILES_libtctisocket-staticdev = "${libdir}/libtcti-socket.*a"
FILES_resourcemgr = "${sbindir}/resourcemgr ${systemd_system_unitdir}/resourcemgr.service"
