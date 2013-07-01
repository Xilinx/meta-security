SUMMARY = "e-mail filter"
DESCRIPTION = "SpamAssassin is a mail filter which attempts to identify spam using a variety of mechanisms including text analysis, Bayesian filtering, DNS blocklists, and collaborative filtering databases."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
DEPENDS = "perl"

SRC_URI = "http://apache.mirrors.hoobly.com/spamassassin/source/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "d1d62cc5c6eac57e88c4006d9633b81e"
SRC_URI[sha256sum] = "5323038939a0ef9fc97d5264defce3ae1d95e98b3a94c4c3b583341c927f32df"

EXTRA_CPANFLAGS = "EXPATLIBPATH=${STAGING_LIBDIR} EXPATINCPATH=${STAGING_INCDIR}"
inherit cpan

do_compile(){
    export LIBC="$(find ${STAGING_DIR_TARGET}/${base_libdir}/ -name 'libc-*.so')"
    cpan_do_compile
}
