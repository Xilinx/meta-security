DESCRIPTION = "The module OpenVAS-CLI collects command line tools to handle with the OpenVAS services via the respective protocols."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "gnutls openvas-libraries glib-2.0"

SRC_URI = "http://wald.intevation.org/frs/download.php/1323/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "e712eb71f3a13cc1b70b50f696465f8e"
SRC_URI[sha256sum] = "d195ca01a44940d1e6fd2ad54ee4fc9b57a3d103235f0a1f05a8b35d97db6be8"

inherit cmake pkgconfig
