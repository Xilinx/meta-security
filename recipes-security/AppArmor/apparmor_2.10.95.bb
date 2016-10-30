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

DEPENDS = "bison-native apr apache2"

SRC_URI = " \
	http://archive.ubuntu.com/ubuntu/pool/main/a/${BPN}/${BPN}_${PV}.orig.tar.gz \
	file://disable_pdf.patch \
	file://apparmor.rc \
	file://functions \
	file://apparmor \
	file://apparmor.service \
	"

SRC_URI[md5sum] = "71a13b9d6ae0bca4f5375984df1a51e7"
SRC_URI[sha256sum] = "3f659a599718f4a5e2a33140916715f574a5cb3634a6b9ed6d29f7b0617e4d1a"

PARALLEL_MAKE = ""

inherit pkgconfig autotools-brokensep update-rc.d python-dir ${@bb.utils.contains('VIRTUAL-RUNTIME_init_manager','systemd','systemd','', d)}

S = "${WORKDIR}/apparmor-${PV}"

PACKAGECONFIG ?="man"
PACKAGECONFIG[man] = "--enable-man-pages, --disable-man-pages"

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
	cd ${S}/libraries/libapparmor
	oe_runmake
        cd ${S}/binutils
        oe_runmake
        cd ${S}/utils
        oe_runmake
	cd ${S}/parser
        oe_runmake
	cd ${S}/profiles
        oe_runmake

 	cd ${S}/changehat/mod_apparmor
        oe_runmake

	if test -z "${PAMLIB}" ; then
		cd ${S}/changehat/pam_apparmor
        	oe_runmake
	fi
}

do_install () {
	install -d ${D}/${INIT_D_DIR}
	install -d ${D}/lib/apparmor
		
	cd ${S}/libraries/libapparmor
	oe_runmake DESTDIR="${D}" install

        cd ${S}/binutils
	oe_runmake DESTDIR="${D}" install

        cd ${S}/utils
	oe_runmake DESTDIR="${D}" install

	cd ${S}/parser
	oe_runmake DESTDIR="${D}" install

	cd ${S}/profiles
	oe_runmake DESTDIR="${D}" install

 	cd ${S}/changehat/mod_apparmor
	oe_runmake DESTDIR="${D}" install

	if test -z "${PAMLIB}" ; then
		cd ${S}/changehat/pam_apparmor
		oe_runmake DESTDIR="${D}" install
	fi

	install ${WORKDIR}/apparmor ${D}/${INIT_D_DIR}/apparmor

	install ${WORKDIR}/functions ${D}/lib/apparmor
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

RDEPENDS_${PN} += "bash perl"
