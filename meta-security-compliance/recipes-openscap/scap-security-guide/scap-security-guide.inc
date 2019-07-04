# Copyright (C) 2017 Armin Kuster  <akuster808@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMARRY = "SCAP content for various platforms"
HOME_URL = "https://www.open-scap.org/security-policies/scap-security-guide/"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97662e4486d9a1d09f358851d9f41a1a"
LICENSE = "LGPL-2.1"

DEPENDS = "openscap-native python3 python3-pyyaml-native python3-jinja2-native libxml2-native"

S = "${WORKDIR}/git"

inherit cmake pkgconfig python3native

#PARALLEL_MAKE = ""

STAGING_OSCAP_BUILDDIR = "${TMPDIR}/work-shared/openscap/oscap-build-artifacts"

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += "-DENABLE_PYTHON_COVERAGE=OFF"

do_configure_prepend () {
	sed -i -e 's:NAMES\ sed:NAMES\ ${HOSTTOOLS_DIR}/sed:g'   ${S}/CMakeLists.txt
        sed -i 's:/usr/share/openscap/:${STAGING_OSCAP_BUILDDIR}${datadir_native}/openscap/:g' ${S}/cmake/SSGCommon.cmake
}

do_compile () {
	cd ${S}/build
	cmake ../
	# oddly rhel7 needs to build first
	make rhel7
}

do_install () {
	cd ${S}/build
	make DESTDIR=${D} install
}

localdatadir = "${prefix}/local/share"
localmandir = "${localdatadir}/man"
localdocdir = "${localdatadir}/doc"
localxmldir = "${localdatadir}/xml"

FILES_${PN} += "${localdatadir} ${localxmldir}"
FILES_${PN}-doc += "${localmandir} ${localdocdir}"
RDEPNEDS_${PN} = "openscap"
