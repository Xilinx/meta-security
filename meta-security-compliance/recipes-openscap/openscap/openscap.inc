# Copyright (C) 2017 Armin Kuster  <akuster808@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMARRY = "NIST Certified SCAP 1.2 toolkit"
HOME_URL = "https://www.open-scap.org/tools/openscap-base/"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"
LICENSE = "LGPL-2.1"

DEPENDS = "autoconf-archive dbus acl bzip2 pkgconfig gconf procps curl libxml2 libxslt libcap swig libgcrypt chrpath-replacement-native "

DEPENDS_class-native = "autoconf-archive-native pkgconfig-native swig-native curl-native libxml2-native libxslt-native dpkg-native libgcrypt-native nss-native"

inherit cmake pkgconfig python3native perlnative ptest

S = "${WORKDIR}/git"

PACKAGECONFIG ?= "python3 rpm perl"
PACKAGECONFIG[python3] = "-DENABLE_PYTHON3=True, , python3, python3"
PACKAGECONFIG[perl] = "-DENABLE_PERL=True,, perl, perl"
PACKAGECONFIG[rpm] = "-DENABLE_OSCAP_UTIL_AS_RPM=True, ,rpm, rpm"

EXTRA_OECONF += "-DENABLE_PROBES_INDEPENDENT=yes -DENABLE_PROBES_LINUX=yes -DWITH_CRYPTO=gcrypt\
		-DENABLE_PROBES_SOLARIS=yes -DENABLE_PROBES_UNIX=yes  -DENABLE_TESTS=no \
		-DENABLE_OSCAP_UTIL_SSH=yes -DENABLE_OSCAP_UTIL=yes -DENABLE_SCE=yes \
                -DENABLE_OSCAP_UTIL_DOCKER=no \
                "

EXTRA_OECONF_class-native += "-DENABLE_PROBES=True"

STAGING_OSCAP_DIR = "${TMPDIR}/work-shared/${MACHINE}/oscap-source"
STAGING_OSCAP_BUILDDIR = "${TMPDIR}/work-shared/openscap/oscap-build-artifacts"

EXTRANATIVEPATH += "chrpath-native"

do_configure_append_class-native () {
	sed -i 's:OSCAP_DEFAULT_CPE_PATH.*$:OSCAP_DEFAULT_CPE_PATH "${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/cpe":' ${S}/config.h
	sed -i 's:OSCAP_DEFAULT_SCHEMA_PATH.*$:OSCAP_DEFAULT_SCHEMA_PATH "${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/schemas":' ${S}/config.h
	sed -i 's:OSCAP_DEFAULT_XSLT_PATH.*$:OSCAP_DEFAULT_XSLT_PATH "${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/xsl":' ${S}/config.h
}

do_clean[cleandirs] += " ${STAGING_OSCAP_BUILDDIR}"

do_compile_append_class-target() {
	sed -i -e 's:${STAGING_BINDIR_NATIVE}/python3-native/python3:${bindir}/python3:' ${B}/utils/oscap-docker
}

do_install_append_class-native () {
	oscapdir=${STAGING_OSCAP_BUILDDIR}/${datadir_native}
	install -d $oscapdir	
	cp -a ${D}/${STAGING_DATADIR_NATIVE}/openscap $oscapdir
}

TESTDIR = "tests"

do_compile_ptest() {
    oe-runcmake ${TESTDIR} 
}

do_install_ptest() {
    # install the tests
    cp -rf ${B}/${TESTDIR} ${D}${PTEST_PATH}
}

do_fixup_rpath() {
	chrpath -d ${D}${libdir}/libopenscap.so.25.0.1
	chrpath -d ${D}${libdir}/libopenscap_sce.so.25.0.1
	chrpath -d ${D}${bindir}/oscap
}

addtask fixup_rpath before do_package after do_install

FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS_${PN} += "libxml2 python3 libgcc"
RDEPENDS_${PN}-ptest = "bash perl python3-core"

BBCLASSEXTEND = "native"
