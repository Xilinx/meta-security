SUMMARY = "Tools for TPM2."
DESCRIPTION = "tpm2.0-tools"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=91b7c548d73ea16537799e8060cea819"
SECTION = "tpm"

DEPENDS = "pkgconfig tpm2.0-tss openssl curl autoconf-archive"

# Mar 17, 2017
SRCREV = "0b744d1b13ce57b4be547ae773d7db1cbccf9a04"

SRC_URI = "git://github.com/01org/tpm2.0-tools.git;protocol=git;branch=master;name=tpm2.0-tools;destsuffix=tpm2.0-tools"

S = "${WORKDIR}/tpm2.0-tools"

PV = "git${SRCPV}"

inherit autotools pkgconfig


