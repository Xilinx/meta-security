SUMMARY = "AppArmor another MAC control system"
DESCRIPTION = "user-space parser utility for AppArmor \
 This provides the system initialization scripts needed to use the \
 AppArmor Mandatory Access Control system, including the AppArmor Parser \
 which is required to convert AppArmor text profiles into machine-readable \
 policies that are loaded into the kernel for use with the AppArmor Linux \
 Security Module."
HOMEAPAGE = "http://apparmor.net/"
SECTION = "admin"

LICENSE = "GPLv2 & GPLv2+ & BSD-3-Clause & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=fd57a4b0bc782d7b80fd431f10bbf9d0"

DEPENDS = "bison-native apr apache2 gettext-native coreutils-native"

SRC_URI = " \
	http://archive.ubuntu.com/ubuntu/pool/main/a/${BPN}/${BPN}_${PV}.orig.tar.gz \
	file://apparmor.rc \
	file://functions \
	file://apparmor \
	file://apparmor.service \
        file://run-ptest \
	"

SRC_URI[md5sum] = "899fd834dc5c8ebf2d52b97e4a174af7"
SRC_URI[sha256sum] = "b1c489ea11e7771b8e6b181532cafbf9ebe6603e3cb00e2558f21b7a5bdd739a"

PARALLEL_MAKE = ""

inherit pkgconfig autotools-brokensep update-rc.d python-dir perlnative ptest
inherit ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','systemd','systemd','', d)}

S = "${WORKDIR}/apparmor-${PV}"

PACKAGECONFIG ?="man"
PACKAGECONFIG[man] = "--enable-man-pages, --disable-man-pages"
PACKAGECONFIG[python] = "--with-python, --without-python, python swig-native"
PACKAGECONFIG[perl] = "--with-perl, --without-perl, perl perl-native"

PAMLIB="${@bb.utils.contains('DISTRO_FEATURES', 'pam', '1', '0', d)}"

do_configure() {
	cd ${S}/libraries/libapparmor
	autoconf --force
	libtoolize --automake -c
	automake -ac
	./configure ${CONFIGUREOPTS} ${EXTRA_OECONF}
	sed -i -e 's#^YACC.*#YACC := bison#' ${S}/parser/Makefile
	sed -i -e 's#^LEX.*#LEX := flex#' ${S}/parser/Makefile
}

do_compile () {
	oe_runmake -C ${B}/libraries/libapparmor
        oe_runmake -C ${B}/binutils
        oe_runmake -C ${B}/utils
        oe_runmake -C ${B}/parser
        oe_runmake -C ${B}/profiles
        oe_runmake -C ${B}/changehat/mod_apparmor

	if test -z "${PAMLIB}" ; then
        	oe_runmake -C ${B}/changehat/pam_apparmor
	fi
}

do_install () {
	install -d ${D}/${INIT_D_DIR}
	install -d ${D}/lib/apparmor
		
	oe_runmake -C ${B}/libraries/libapparmor DESTDIR="${D}" install
	oe_runmake -C ${B}/binutils DESTDIR="${D}" install
	oe_runmake -C ${B}/utils DESTDIR="${D}" install
	oe_runmake -C ${B}/parser DESTDIR="${D}" install
	oe_runmake -C ${B}/profiles DESTDIR="${D}" install
	oe_runmake -C ${B}/changehat/mod_apparmor DESTDIR="${D}" install

	if test -z "${PAMLIB}" ; then
		oe_runmake -C ${B}/changehat/pam_apparmor DESTDIR="${D}" install
	fi

	install ${WORKDIR}/apparmor ${D}/${INIT_D_DIR}/apparmor
	install ${WORKDIR}/functions ${D}/lib/apparmor
}

do_compile_ptest () {
        oe_runmake -C ${B}/tests/regression/apparmor
        oe_runmake -C ${B}/parser/tst
        oe_runmake -C ${B}/libraries/libapparmor
}

do_install_ptest () {
	t=${D}/${PTEST_PATH}/testsuite
	install -d ${t}
	install -d ${t}/tests/regression/apparmor
	cp -rf ${B}/tests/regression/apparmor ${t}/tests/regression

	install -d ${t}/parser/tst
	cp -rf ${B}/parser/tst ${t}/parser
	cp ${B}/parser/apparmor_parser ${t}/parser
	cp ${B}/parser/frob_slack_rc ${t}/parser

	install -d ${t}/libraries/libapparmor
	cp -rf ${B}/libraries/libapparmor ${t}/libraries

	install -d ${t}/common
	cp -rf ${B}/common ${t}

	install -d ${t}/binutils
	cp -rf ${B}/binutils ${t}
}

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "apparmor"
INITSCRIPT_PARAMS = "start 16 2 3 4 5 . stop 35 0 1 6 ."

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "apparmor.service"
SYSTEMD_AUTO_ENABLE = "disable"

PACKAGES += "python-${PN} mod-${PN}"

FILES_${PN} += "/lib/apparmor/ ${sysconfdir}/apparmor"
FILES_mod-${PN} = "${libdir}/apache2/modules/*"
FILES_python-${PN} = "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS_${PN} += "bash lsb"
RDEPENDS_${PN}-ptest += "coreutils dbus-lib"
