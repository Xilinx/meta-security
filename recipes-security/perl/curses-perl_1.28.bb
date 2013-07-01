DESCRIPTION = "This package contains the URI.pm module with friends. \
The module implements the URI class. URI objects can be used to access \
and manipulate the various components that make up these strings."

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"
PR = "r0"

LIC_FILES_CHKSUM = "file://README;beginline=26;endline=30;md5=0b37356c5e9e28080a3422d82af8af09"

DEPENDS += "perl ncurses"

SRC_URI = "http://www.cpan.org/authors/id/G/GI/GIRAFFED/Curses-1.28.tgz"

SRC_URI[md5sum] = "ed9f7ddf2d90f4266da91c3dc9fad9c9"
SRC_URI[sha256sum] = "613b73c4b6075b1550592812214e4fc0e2205d3afcf234e3fa90f208fb8de892"

S = "${WORKDIR}/Curses-${PV}"

EXTRA_CPANFLAGS = "EXPATLIBPATH=${STAGING_LIBDIR} EXPATINCPATH=${STAGING_INCDIR}"

inherit cpan

do_compile() {
	export LIBC="$(find ${STAGING_DIR_TARGET}/${base_libdir}/ -name 'libc-*.so')"
	cpan_do_compile
}
