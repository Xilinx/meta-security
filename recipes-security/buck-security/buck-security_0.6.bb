SUMMARY = "Linux security scanner"
DESCRIPTION = "Buck-Security is a security scanner for Debian and Ubuntu Linux. It runs a couple of important checks and helps you to harden your Linux \
system. This enables you to quickly overview the security status of your Linux system."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "perl perl-module-term-ansicolor perl-module-posix perl-module-getopt-long perl-module-time-localtime perl-module-data-dumper perl-module-lib net-tools coreutils gnupg pinentry"

SRC_URI = "http://sourceforge.net/projects/buck-security/files/buck-security/buck-security_0.6/${PN}_${PV}.tar.gz \
           file://functionality.patch"

SRC_URI[md5sum] = "edbd40742853fc91ffeae5b2d9ea7bab"
SRC_URI[sha256sum] = "5d5dcc58b09c3a4bd87f60f86bb62cd2b0bfd7106a474951f8f520af0042a5b7"

S = "${WORKDIR}/${PN}_${PV}"

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    install -d ${D}${bindir}/buck
    cp -r ${S}/* ${D}${bindir}/buck
    cp -r ${S}/buck-security ${D}${bindir}
    sed -i 's:use lib "checks":use lib "${bindir}/buck/checks":g' ${D}${bindir}/buck-security
    sed -i 's:use lib "checks/lib":use lib "${bindir}/buck/checks/lib":g' ${D}${bindir}/buck-security
    sed -i 's:use lib "lib":use lib "${bindir}/buck/lib":g' ${D}${bindir}/buck-security
    sed -i 's:conf/buck-security.conf:${bindir}/buck/conf/buck-security.conf:g' ${D}${bindir}/buck-security

}

FILES_${PN} = "${bindir}/*"
