DESCRIPTION = "This package contains the DNS.pm module with friends."

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://README;md5=f21d77d9c6b56a07470bbce561b169e3"

DEPENDS += "perl"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/N/NL/NLNETLABS/Net-DNS-${PV}.tar.gz"

SRC_URI[md5sum] = "393e48ec6f28abe5ed30204276e02775"
SRC_URI[sha256sum] = "a62cae0be54a9684c305456cb95515a0bd3128d6ef3093b6069fe8e8e8d5943f"

S = "${WORKDIR}/Net-DNS-${PV}"

EXTRA_CPANFLAGS = "EXPATLIBPATH=${STAGING_LIBDIR} EXPATINCPATH=${STAGING_INCDIR}"

inherit cpan

do_compile() {
	export LIBC="$(find ${STAGING_DIR_TARGET}/${base_libdir}/ -name 'libc-*.so')"
	cpan_do_compile
}
BBCLASSEXTEND = "native"
