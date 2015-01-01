SUMMARY = "The Suricata Engine is an Open Source Next Generation Intrusion Detection and Prevention Engine"

require suricata.inc

LIC_FILES_CHKSUM = "file://LICENSE;beginline=1;endline=2;md5=1fbd81241fe252ec0f5658a521ab7dd8"

SRC_URI += "file://no_libhtp_build.patch \
            file://volatiles.03_suricata \
            file://suricata.yaml \
        "

DEPENDS = "libhtp file libpcre libyaml libpcap libcap-ng libnet libnfnetlink"

inherit autotools-brokensep pkgconfig

CFLAGS += "-D_DEFAULT_SOURCE"

EXTRA_OECONF += " --disable-debug \
    --enable-non-bundled-htp \
    --disable-gccmarch-native \
    --with-libpcre-includes=${STAGING_INCDIR} \
    --with-libpcre-libraries=${STAGING_LIBDIR} \
    --with-libyaml-includes=${STAGING_INCDIR} \
    --with-libyaml-libraries=${STAGING_LIBDIR} \
    --with-libpcap-includes=${STAGING_INCDIR} \
    --with-libpcap-libraries=${STAGING_LIBDIR} \
    --with-libcap_ng-includes=${STAGING_INCDIR} \
    --with-libcap_ng-libraries=${STAGING_LIBDIR} \
    --with-libnfnetlink-includes=${STAGING_INCDIR} \
    --with-libnfnetlink-libraries=${STAGING_LIBDIR} \
    --with-libnet-includes=${STAGING_INCDIR} \
    --with-libnet-libraries=${STAGING_LIBDIR} \
    --with-libhtp-includes=${STAGING_INCDIR} \
    --with-libhtp-libraries=${STAGING_LIBDIR} \
    --with-libjansson-includes=${STAGING_INCDIR} \
    --with-libjansson-libraries=${STAGING_LIBDIR} \
    "

export logdir = "${localstatedir}/log"

do_install_append () {
    install -d ${D}${sysconfdir}/suricata
    install -d ${D}${sysconfdir}/suricata ${D}${sysconfdir}/default/volatiles
    install -m 644 classification.config ${D}${sysconfdir}/suricata
    install -m 644 reference.config ${D}${sysconfdir}/suricata
    install -m 644 ${WORKDIR}/suricata.yaml ${D}${sysconfdir}/suricata
    install -m 0644 ${WORKDIR}/volatiles.03_suricata  ${D}${sysconfdir}/default/volatiles/volatiles.03_suricata
}

pkg_postinst_${PN} () {
if [ -z "$D" ] && [ -e /etc/init.d/populate-volatile.sh ] ; then
    ${sysconfdir}/init.d/populate-volatile.sh update
fi
    ${bindir}/suricata -c ${sysconfdir}/suricata.yaml -i eth0 
}

FILES_${PN} += "${sysconfdir}/suricata ${logdir}/suricata"
FILES_${PN}-dev += "/usr/lib/python2.7/site-packages"

RDEPENDS_${PN} += " file jansson"
