SUMMARY = "interface to seccomp filtering mechanism"
DESCRIPTION = "The libseccomp library provides and easy to use, platform independent,interface to the Linux Kernel's syscall filtering mechanism: seccomp."
SECTION = "security"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=0;endline=1;md5=8eac08d22113880357ceb8e7c37f989f"

SRCREV = "bd10aab13c7248cc0df57512617e33d6743d33a6"

PV = "2.2.0+git${SRCPV}"

SRC_URI = "git://github.com/seccomp/libseccomp.git \
        file://0001-arch-add-a-ppc64-syscall-table.patch \
        file://0002-arch-add-the-basic-initial-support-for-ppc64-to-the-.patch \
        file://0003-tools-add-ppc64-support.patch \
        file://0004-tests-add-ppc64-support-to-the-regression-tests.patch \
        file://0005-tests-add-ppc64-support-to-the-regression-live-tests.patch \
        file://0006-ppc64-correct-the-ppc64-syscall-table-and-validation.patch \
        file://0007-tests-minor-fix-in-arch-syscall-check.patch \
        file://0008-arch-add-a-ppc-syscall-table.patch \
        file://0009-arch-add-basic-initial-ppc-support-to-the-arch-depen.patch \
        file://0010-tools-add-ppc-support.patch \
        file://0011-tests-add-ppc-support-to-the-regression-tests.patch \
        file://regresion_fixup_seq_usage.patch \
        "

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

PACKAGECONFIG ??= ""
PACKAGECONFIG[python] = "--enable-python, --disable-python, python"

do_compile_append() {
    oe_runmake -C tests check-build
}

do_install_append() {
    install -d ${D}/${libdir}/${PN}/tests
    install -d ${D}/${libdir}/${PN}/tools
    for file in $(find tests/* -executable -type f); do
        install -m 744 ${S}/${file} ${D}/${libdir}/${PN}/tests
    done
    for file in $(find tests/*.tests -type f); do
        install -m 744 ${S}/${file} ${D}/${libdir}/${PN}/tests
    done
    for file in $(find tools/* -executable -type f); do
        install -m 744 ${S}/${file} ${D}/${libdir}/${PN}/tools
    done
}

PACKAGES += " ${PN}-tests"
FILES_${PN} = "${bindir} ${libdir}/${PN}.so*"
FILES_${PN}-tests = "${libdir}/${PN}/tools ${libdir}/${PN}/tests"
FILES_${PN}-dbg += "${libdir}/${PN}/tests/.debug/* ${libdir}/${PN}/tools/.debug"

RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-tests = "bash"
