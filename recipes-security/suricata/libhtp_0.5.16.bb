SUMMARY = "LibHTP is a security-aware parser for the HTTP protocol and the related bits and pieces."

require suricata.inc

LIC_FILES_CHKSUM = "file://../LICENSE;beginline=1;endline=2;md5=1fbd81241fe252ec0f5658a521ab7dd8"

inherit autotools pkgconfig

CFLAGS += "-D_DEFAULT_SOURCE"

S = "${WORKDIR}/suricata-2.0.5/${BPN}"

RDEPENDS_${PN} += "zlib"
