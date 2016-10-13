SUMMARY = "LIBPM - Software TPM Library"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97e5eea8d700d76b3ddfd35c4c96485f"
SRCREV = "e5dc628043e981c9f8d7711ddfe5812c8f4e38cc"
SRC_URI = "git://github.com/stefanberger/libtpms.git"

S = "${WORKDIR}/git"
inherit autotools-brokensep pkgconfig

PACKAGECONFIG ?= "openssl"
PACKAGECONFIG[openssl] = "--with-openssl, --without-openssl, openssl"

PV = "1.0+git${SRCPV}"

BBCLASSEXTEND = "native"
