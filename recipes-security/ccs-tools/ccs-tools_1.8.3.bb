SUMMARY = "Tomoyo"
DESCRIPTION = "TOMOYO Linux is a Mandatory Access Control (MAC) implementation for Linux that can be used to increase the security of a system, while also being useful purely as a system analysis tool. \nTo start via command line add: \nsecurity=tomoyo TOMOYO_trigger=/usr/lib/systemd/systemd \nTo initialize: \n/usr/lib/ccs/init_policy"

SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://README.ccs;md5=5b80632c6a2a3b7fa92ea46eff15bee9"

DEPENDS = "ncurses"

SRC_URI = "http://osdn.dl.sourceforge.jp/tomoyo/49693/ccs-tools-1.8.3-20130406.tar.gz"

SRC_URI[md5sum] = "ffff535584cdf9ae28b85061c32e5618"
SRC_URI[sha256sum] = "1e4129b59cd7fdb963962af7f2cccf4a66c77a4b0948f67320b569aaf6585fcf"

S = "${WORKDIR}/${PN}"

inherit distro_features_check

do_make(){
    oe_runmake USRLIBDIR=${libdir} all
    cd ${S}/kernel_test
    oe_runmake  all
}

do_install(){
    oe_runmake INSTALLDIR=${D}  USRLIBDIR=${libdir} install
}

PACKAGE="${PN} ${PN}-dbg ${PN}-doc"

FILES_${PN} = "\
    ${sbindir}/* \
    ${base_sbindir}/* \
    ${libdir}/* \
"

FILES_${PN}-doc = "\
    ${mandir}/man8/* \
"

FILES_${PN}-dbg = "\
    ${base_sbindir}/.debug/* \
    ${sbindir}/.debug/* \
    ${libdir}/.debug/* \
    ${libdir}/ccs/.debug/* \
    /usr/src/debug/* \
"

REQUIRED_DISTRO_FEATURES ?=" tomoyo"
