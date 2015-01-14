SUMMARY = "interface to seccomp filtering mechanism"
DESCRIPTION = "The libseccomp library provides and easy to use, platform independent,interface to the Linux Kernel's syscall filtering mechanism: seccomp."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "http://sourceforge.net/projects/libseccomp/files/${BP}.tar.gz \
           file://compiler.patch \
           file://0001-tests-create-install-tests-target.patch \
           file://0002-tests-install-python-tests-if-appropriate.patch \
           file://0003-tests-introduce-alternate-test-report-format.patch \
"

SRC_URI[md5sum] = "1f41207b29e66a7e5e375dd48a64de85"
SRC_URI[sha256sum] = "8812c11e407c383f5ad6afb84a88e5a0224477bcfe8ff03f0c548e5abaac841c"

do_configure() {
   ${S}/configure --prefix=${prefix} --libdir=${libdir}
}

do_compile_append() {
    oe_runmake  DESTDIR=${D} tests
}

do_install() {
    oe_runmake DESTDIR=${D} install
    oe_runmake  DESTDIR=${D} install-tests
}

PACKAGES += "${PN}-tests "
FILES_${PN}-tests = "${libdir}/${BPN}/tests/*"
FILES_${PN}-dbg += "${libdir}/${BPN}/tests/.debug/*"

RDEPENDS_${PN} = "bash"
