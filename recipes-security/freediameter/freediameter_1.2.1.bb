# copyright 2017 Armin Kuster <akuster808@gmail.com>
#

SUMARRY = "freeDiameter is a implementation of the Diameter protocol specified in RFC3588. Diameter is a protocol for carrying Authentication, Authorization and Accounting information."

HOMEPAGE = "http://www.freediameter.net/trac/"
LIC_FILES_CHKSUM = "file://LICENSE;md5=892b2ed6ae815488a08416ff7ee74a35"
LICENSE = "BSD"

DEPENDS = "lksctp-tools libidn gnutls libgcrypt bison flex"

HG_SRCREV = "8662db9f6105"
SRC_URI = " \
	http://www.freediameter.net/hg/freeDiameter/archive/${HG_SRCREV}.tar.bz2 \
	file://init \
        file://freeDiameter.conf \
	file://no_test_run.patch"

SRC_URI[md5sum] = "2fbf09aa1e0eba9ae0b5f1ab05a6d462"
SRC_URI[sha256sum] = "061d9b0d860605f0838c0ab312c3719f45b215bf5e212ad2b45a59d036b670b7"

S = "${WORKDIR}/freeDiameter-${HG_SRCREV}"

inherit cmake pkgconfig update-rc.d 

CONFFILES_${PN} = "${sysconfdir}/freediameter.conf"

EXTRA_OECMAKE += "-DDEFAULT_CONF_PATH:PATH=${sysconfdir}/freeDiameter"

FD_KEY ?="${PN}.key"
FD_PEM ?= "${PN}.pem"
FD_CA ?= "${PN}.pem"
FD_DH_PEM ?= "${PN}-dh.pem"
FD_HOSTNAME ?= "${MACHINE}"
FD_REALM ?= "openembedded.org"

do_install_append () {
	install -d ${D}${sysconfdir}/freeDiameter
	install ${WORKDIR}/freeDiameter.conf ${D}${sysconfdir}/freeDiameter/freeDiameter.conf

	cat >> ${D}${sysconfdir}/freeDiameter/freeDiameter.conf <<EOF
## OE specific ##
#Identity="${FD_HOSTNAME}";
Identity = "${FD_HOSTNAME}.${FD_REALM}";
Realm = "${FD_REALM}";
Port = 30868;
SecPort = 30869;
TLS_Cred = "/etc/freeDiameter/${FD_PEM}" , "/etc/freeDiameter/${FD_KEY}";
TLS_CA = "/etc/freeDiameter/${FD_CA}";
TLS_DH_File = "/etc/freeDiameter/${FD_DH_PEM}";
EOF
	install -d ${D}${sysconfdir}/init.d/
	install ${WORKDIR}/init ${D}${sysconfdir}/init.d/freediameter
}

do_install_append () {
	# create self cert
	openssl req -x509 -config ${STAGING_DIR_NATIVE}/etc/ssl/openssl.cnf -newkey rsa:4096 -sha256 -nodes -out ${D}${sysconfdir}/freeDiameter/${FD_PEM} -keyout ${D}${sysconfdir}/freeDiameter/${FD_KEY} -days 3650 -subj '/CN=${FD_HOSTNAME}.${FD_REALM}'
 	openssl dhparam -out ${D}${sysconfdir}/freeDiameter/${FD_DH_PEM} 1024
}

PACKAGES += "${PN}-extensions"
FILES_${PN}-extensions = "${nonarch_libdir}/freeDiameter/*.fdx"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "freediameter"
INITSCRIPT_PARAMS_${PN} = "defaults 80"

RDEPENDS_${PN} = "openssl openssl-conf openssl-engines"
RDEPENDS_${PN} += "kernel-module-tipc kernel-module-sctp" 
RDEPENDS_${PN} += "kernel-module-udp-tunnel kernel-module-ipip"
