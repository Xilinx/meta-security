SUMMARY = "Tools for TPM2."
DESCRIPTION = "tpm2.0-tools"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=91b7c548d73ea16537799e8060cea819"
SECTION = "tpm"

DEPENDS = "tpm2.0-tss openssl curl"

SRCREV = "c924cc8ca752e5af6a829f893c6be4b185d56e99"

SRC_URI = "git://github.com/01org/tpm2.0-tools.git;protocol=git;branch=master;name=tpm2.0-tools;destsuffix=tpm2.0-tools"

S = "${WORKDIR}/tpm2.0-tools"

PV = "git${SRCPV}"

inherit autotools-brokensep pkgconfig

do_configure () {
	cd ${S}
	./bootstrap  --force
	./configure ${CONFIGUREOPTS} ${EXTRA_OECONF}
}
