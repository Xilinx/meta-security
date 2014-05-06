SUMMARY = "network auditing tool"
DESCRIPTION = "Nmap ("Network Mapper") is a free and open source (license) utility for network discovery and security auditing."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
FILES_${PN} += "${target_datadir}/ncat"

SRC_URI = "http://nmap.org/dist/${PN}-${PV}.tar.bz2 \
           file://lua.patch"

SRC_URI[md5sum] = "fcc80f94ff3adcb11eedf91092ea6f5e"
SRC_URI[sha256sum] = "3349cc6d36b86b95ca2b8075d16615a3a598cef494920d6652f9a8bf9f7660b5"

inherit autotools-brokensep

DEPENDS = "libpcap"

EXTRA_OECONF = "--without-liblua --without-zenmap --without-subversion --with-pcap=linux"

do_configure() {
    autoconf
    oe_runconf
}

