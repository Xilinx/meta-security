SUMMARY = "network auditing tool"
DESCRIPTION = "Nmap ("Network Mapper") is a free and open source (license) utility for network discovery and security auditing."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
FILES_${PN} += "${target_datadir}/ncat"

SRC_URI = "http://nmap.org/dist/${BPN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "5a36ad3a63d5b7ea5514f745a397436a"
SRC_URI[sha256sum] = "3f89d9053c69507fe9533c40188a6561d49607a37b1db6380aed9039d4883137"

inherit autotools-brokensep

DEPENDS = "libpcap"

EXTRA_OECONF = "--without-liblua --without-zenmap --without-subversion --with-pcap=linux"

do_configure() {
    autoconf
    oe_runconf
}

# should add a conditional for "--without-zenmap" test. 
# zenmap builds if the below are uncommented. Not tested
#PACKAGES =+ "${PN}-zenmap"
#FILES_${PN}-zenmap = "/usr/share/zenmap/*"

#  should add a conditional based for "--without-ndiff"
PACKAGES =+ "${PN}-python"
DESCRIPTION_${PN}-python = \
"The ${PN}-python package includes the ndiff utility. \
Ndiff is a tool to aid in the comparison of Nmap scans."
FILES_${PN}-python = "${libdir}/python${PYTHON_BASEVERSION}/site-packages/*"


