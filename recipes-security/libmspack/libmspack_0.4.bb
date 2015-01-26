SUMMARY = "A library for Microsoft compression formats"
HOMEPAGE = "http://www.cabextract.org.uk/libmspack/"
SECTION = "lib"
LICENSE = "LGPL-2.1"
DEPENDS = ""

LIC_FILES_CHKSUM = "file://COPYING.LIB;beginline=1;endline=2;md5=5b1fd1f66ef926b3c8a5bb00a72a28dd"

SRC_URI = "${DEBIAN_MIRROR}/main/libm/${BPN}/${BPN}_${PV}.orig.tar.gz\
"
SRC_URI[md5sum] = "1ab10b507259993c74b4c41a88103b59"
SRC_URI[sha256sum] = "b7958983f60cbec40d48ef87520210f9d8e2672a57a5ce0a2187b3d4fb2f5c68"

inherit autotools

S = "${WORKDIR}/${BP}alpha"
