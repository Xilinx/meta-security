SUMMARY = "interface to seccomp filtering mechanism"
DESCRIPTION = "The libseccomp library provides and easy to use, platform independent,interface to the Linux Kernel's syscall filtering mechanism: seccomp."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "http://sourceforge.net/projects/libseccomp/files/${PN}-${PV}.tar.gz \
           file://compiler.patch"

SRC_URI[md5sum] = "3961103c1234c13a810f6a12e60c797f"
SRC_URI[sha256sum] = "b0d6e4f0984e6632a04f0cf33c6babdb011674ba15ff208e196f037e0e09905e"

do_configure() {
   ${S}/configure --prefix=${prefix} --libdir=${libdir}
}

do_install() {
    oe_runmake DESTDIR=${D} install
}
