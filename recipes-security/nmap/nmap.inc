SUMMARY = "network auditing tool"
DESCRIPTION = "Nmap ("Network Mapper") is a free and open source (license) utility for network discovery and security auditing.\nGui support via appending to IMAGE_FEATURES x11-base in local.conf"
SECTION = "security"
LICENSE = "GPL-2.0"

inherit autotools-brokensep pkgconfig distro_features_check

PACKAGECONFIG = "ncat nping ndiff pcap lua"
PACKAGECONFIG += " ${@bb.utils.contains("IMAGE_FEATURES", "x11-base", "zenmap", "", d)}"

PACKAGECONFIG[pcap] = "--with-pcap=linux, --without-pcap, libpcap, libpcap"
PACKAGECONFIG[ssl] = "--with-openssl=${STAGING_LIBDIR}/.., --without-openssl, openssl, openssl"

#disable/enable packages
PACKAGECONFIG[nping] = ",--without-nping,"
PACKAGECONFIG[ncat] = ",--without-ncat,"
PACKAGECONFIG[ndiff] = ",--without-ndiff,"

#use nmap's Included or system's libs
PACKAGECONFIG[lua] = "--with-liblua=${STAGING_LIBDIR}/.., --without-liblua, lua"
PACKAGECONFIG[pcre] = "--with-libpcre=${STAGING_LIBDIR}/.., --with-libpcre=included, libpre"

#Add gui
PACKAGECONFIG[zenmap] = "--with-zenmap, --without-zenmap, gtk+ python-core python-codecs python-io python-logging python-unittest python-xml python-netclient python-doctest python-subprocess python-pygtk, python-core python-codecs python-io python-logging python-netclient python-xml python-unittest python-doctest python-subprocess  python-pygtk gtk+"

EXTRA_OECONF = "--with-libdnet=included --with-liblinear=included --without-subversion"

do_configure() {
    autoconf
    oe_runconf
}

PACKAGES = "${PN} ${PN}-dbg ${PN}-doc"

FILES_${PN} = "${bindir}/nmap ${datadir}/nmap/*"

# append packages if enabled
FILES_${PN} += "${@bb.utils.contains("PACKAGECONFIG", "ncat", "${bindir}/ncat ${target_datadir}/ncat", "", d)}"
FILES_${PN} += "${@bb.utils.contains("PACKAGECONFIG", "nping", "${bindir}/nping", "", d)}"
FILES_${PN} += "${@bb.utils.contains("PACKAGECONFIG", "ndiff", "${bindir}/ndiff ${libdir}/python${PYTHON_BASEVERSION}/site-packages/ndiff.py*", "", d)}"

PACKAGES += "${@bb.utils.contains("PACKAGECONFIG", "zenmap", "${PN}-zenmap", "", d)}"

FILES_${PN}-zenmap = "${@bb.utils.contains("PACKAGECONFIG", "zenmap", "${bindir}/*zenmap ${bindir}/xnmap ${datadir}/applications/*  ${bindir}/nmapfe ${datadir}/zenmap/* ${libdir}/python${PYTHON_BASEVERSION}/site-packages/radialnet/* ${libdir}/python${PYTHON_BASEVERSION}/site-packages/zenmap*", "", d)}"

RDEPENDS_${PN}-zenmap = "nmap"
