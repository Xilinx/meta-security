SUMMARY = "The tpm-tools package contains commands to allow the platform administrator the ability to manage and diagnose the platform's TPM."
DESCRIPTION = " \
  The tpm-tools package contains commands to allow the platform administrator \
  the ability to manage and diagnose the platform's TPM.  Additionally, the \
  package contains commands to utilize some of the capabilities available \
  in the TPM PKCS#11 interface implemented in the openCryptoki project. \
  "
SECTION = "tpm"
LICENSE = "CPL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=059e8cd6165cb4c31e351f2b69388fd9"

DEPENDS = "libtspi openssl"
DEPENDS_class-native = "trousers-native"

SRCREV = "80954ab83be8d091c6e3112514945556aaa09d39"
SRC_URI = " \
	git://git.code.sf.net/p/trousers/tpm-tools \
	file://tpm-tools-extendpcr.patch \
	"

PV = "1.3.9+git${SRCPV}"

inherit autotools-brokensep gettext

S = "${WORKDIR}/git"

do_configure_prepend () {
	mkdir -p po
	mkdir -p m4
	cp -R po_/* po/
	touch po/Makefile.in.in
	touch m4/Makefile.am
}

BBCLASSEXTEND = "native"
