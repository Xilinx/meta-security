DESCRIPTION = "basic system security checks"
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "http://ftp.de.debian.org/debian/pool/main/c/checksecurity/checksecurity_${PV}.tar.gz"

SRC_URI[md5sum] = "ad6cfe0cd66ebdd16dd5d4ee5fa8fa17"
SRC_URI[sha256sum] = "a2bc2355358d6daf3cb72485d564e82cb541e8516f23b50522c816853ecd13c2"

do_compile() {    
}

do_install() {
    oe_runmake PREFIX=${D}
}
