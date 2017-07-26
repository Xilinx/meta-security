SUMMARY = "Keynote tool and library"
DESCRIPTION = "KeyNote is a simple and flexible trust-management \
  system designed to work well for a variety of large- and small- \
  scale Internet-based applications. \
"
HOMEPAGE = "http://www.cs.columbia.edu/~angelos/keynote.html"
SECTION = "security"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3a265095c549c1808686a676f2699c98"

SRC_URI = "http://www.cs.columbia.edu/~angelos/Code/${BPN}.tar.gz \
       file://configure-remove-hardcode-path.patch \
       file://makefile-add-ldflags.patch \
       file://run-ptest \
"

inherit autotools-brokensep ptest

SRC_URI[md5sum] = "ba58a0297c421dc6aa671e6b753ef695"
SRC_URI[sha256sum] = "62f7a9d57ceb6bcdd47b604b637a7ac8ed337cef0ab02f1fa28b7e61c9b15821"

DEPENDS = "flex openssl"

EXTRA_OEMAKE += "test-sample -j1"

do_install() {
    install -D -m 0755 ${S}/keynote ${D}${bindir}/keynote
    install -D -m 0644 ${S}/libkeynote.a ${D}${libdir}/libkeynote.a
    install -D -m 0644 ${S}/keynote.h ${D}${includedir}/keynote.h
}

do_install_ptest() {
    install -D -m 0755 ${S}/sample-app ${D}${PTEST_PATH}
    cp -r ${S}/testsuite ${D}${PTEST_PATH}
    sed -i 's|@PTEST_PATH@|${PTEST_PATH}|' ${D}${PTEST_PATH}/run-ptest
}
