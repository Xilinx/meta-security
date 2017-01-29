SUMMARY = "LIBPM - Software TPM Library"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97e5eea8d700d76b3ddfd35c4c96485f"

SRCREV = "ad44846dda5a96e269ad2f78a532e01e9a2f02a1"
SRC_URI = " \
	git://github.com/stefanberger/libtpms.git \
	file://fix_dprintf_issue.patch \
	"

S = "${WORKDIR}/git"
inherit autotools-brokensep pkgconfig

PACKAGECONFIG ?= "openssl"
PACKAGECONFIG[openssl] = "--with-openssl, --without-openssl, openssl"

PV = "1.0+git${SRCPV}"

BBCLASSEXTEND = "native"
