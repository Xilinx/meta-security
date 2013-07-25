DESCRIPTION = "The libcap-ng library is intended to make programming with posix capabilities much easier than the traditional libcap library."
HOMEPAGE = "http://people.redhat.com/sgrubb/libcap-ng/index.html"
LICENSE = "GPL-2.0"
DEPENDS = "libcap"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "http://people.redhat.com/sgrubb/libcap-ng/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "610afb774f80a8032b711281df126283"
SRC_URI[sha256sum] = "5ca441c8d3a1e4cfe8a8151907977662679457311ccaa7eaac91447c33a35bb1"

inherit autotools
