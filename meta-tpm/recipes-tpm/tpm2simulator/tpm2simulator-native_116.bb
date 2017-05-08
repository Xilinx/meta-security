SUMMARY = "TPM 2.0 Simulator Extraction Script"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1415f7be284540b81d9d28c67c1a6b8b"

DEPENDS += "python"

SRCREV = "93dc4412432013ed7bcabd42007754c68f6e362e"
SRC_URI = "git://github.com/stwagnr/tpm2simulator.git"

S = "${WORKDIR}/git"
OECMAKE_SOURCEPATH = "${S}/cmake"

PV = "116+git${SRCPV}"

inherit native lib_package cmake

EXTRA_OECMAKE = " \
	-DCMAKE_BUILD_TYPE=Debug \
	-DSPEC_VERSION=116 \
"

do_configure_prepend () {
	sed -i 's/^SET = False/SET = True/' ${S}/scripts/settings.py 
}
