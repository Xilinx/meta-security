DESCRIPTION = "Perl module for using OpenSSL"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://README;md5=94ec8036b939a2e3c9b5e7f10af8f4ee"

DEPENDS += "perl openssl"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/M/MI/MIKEM/Net-SSLeay-${PV}.tar.gz"

SRC_URI[md5sum] = "473b8d66ca69d5784bb0e428721f58e0"
SRC_URI[sha256sum] = "8cd5f09722e07b4e436102cb3a4b93623d753c171665ca9752c3b39a62ea3a79"

S = "${WORKDIR}/Net-SSLeay-${PV}"

EXTRA_CPANFLAGS = "INC='-I${STAGING_INCDIR}' LIBS='-L${STAGING_LIBDIR} -lssl -lcrypto -lz'"

inherit cpan

do_compile() {
	export LIBC="$(find ${STAGING_DIR_TARGET}/${base_libdir}/ -name 'libc-*.so')"
	cpan_do_compile
}
