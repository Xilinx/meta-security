SUMMARY = "network auditing tool"
DESCRIPTION = "Nmap ("Network Mapper") is a free and open source (license) utility for network discovery and security auditing.\nGui support via appending to IMAGE_FEATURES x11-base in local.conf"
SECTION = "security"
LICENSE = "GPL-2.0"

LIC_FILES_CHKSUM = "file://COPYING;beginline=7;endline=12;md5=87c6956e28c3603a0a1dda11bcdc227a"

SRC_URI = "http://nmap.org/dist/${BP}.tar.bz2 \
           file://nmap-redefine-the-python-library-dir.patch \
           file://nmap-replace-shtool-mkdir-with-coreutils-mkdir-command.patch \
"

SRC_URI[md5sum] = "435c7e095bdd4565e0f69c41743a45be"
SRC_URI[sha256sum] = "e9a96a8e02bfc9e80c617932acc61112c23089521ee7d6b1502ecf8e3b1674b2"

inherit autotools-brokensep pkgconfig pythonnative distro_features_check

PACKAGECONFIG ?= "ncat nping ndiff pcap"
PACKAGECONFIG += " ${@bb.utils.contains('IMAGE_FEATURES', 'x11-base', 'zenmap', '', d)}"

PACKAGECONFIG[pcap] = "--with-pcap=linux, --without-pcap, libpcap, libpcap"
PACKAGECONFIG[pcre] = "--with-libpcre=${STAGING_LIBDIR}/.., --with-libpcre=included, libpre"
PACKAGECONFIG[ssl] = "--with-openssl=${STAGING_LIBDIR}/.., --without-openssl, openssl, openssl"

#disable/enable packages
PACKAGECONFIG[nping] = ",--without-nping,"
PACKAGECONFIG[ncat] = ",--without-ncat,"
PACKAGECONFIG[ndiff] = ",--without-ndiff,python"
PACKAGECONFIG[update] = ",--without-nmap-update,"

#Add gui
PACKAGECONFIG[zenmap] = "--with-zenmap, --without-zenmap, gtk+ python-core python-codecs python-io python-logging python-unittest python-xml python-netclient python-doctest python-subprocess python-pygtk, python-core python-codecs python-io python-logging python-netclient python-xml python-unittest python-doctest python-subprocess  python-pygtk gtk+"

EXTRA_OECONF = "--with-libdnet=included --with-liblinear=included --without-subversion --with-liblua=included"

export PYTHON_SITEPACKAGES_DIR

do_configure() {
    # strip hard coded python2#
    sed -i -e 's=python2\.*=python=g'  ${S}/configure.ac
    sed -i -e 's=python2\.*=python=g'  ${S}/configure
    autoconf
    oe_runconf
}

PACKAGES += "${@bb.utils.contains('PACKAGECONFIG', 'zenmap', '${PN}-zenmap', '', d)}"

FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR}"
FILES_${PN}-zenmap = "${@bb.utils.contains("PACKAGECONFIG", "zenmap", "${bindir}/*zenmap ${bindir}/xnmap ${datadir}/applications/*  ${bindir}/nmapfe ${datadir}/zenmap/* ${PYTHON_SITEPACKAGES_DIR}/radialnet/* ${PYTHON_SITEPACKAGES_DIR}/zenmap*", "", d)}"

RDEPENDS_${PN} = "python"
RDEPENDS_${PN}-zenmap = "nmap"
