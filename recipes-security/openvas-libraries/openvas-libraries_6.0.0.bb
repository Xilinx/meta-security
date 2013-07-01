DESCRIPTION = "This is the libraries module for the Open Vulnerability Assessment System (OpenVAS)."
SECTION = "security"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "bison flex gpgme glib-2.0"

SRC_URI = "http://wald.intevation.org/frs/download.php/1303/${PN}-${PV}.tar.gz \
           file://g_type_init.patch"

SRC_URI[md5sum] = "0e8b73ee4ad5b36984b5d7be5d6bdfc0"
SRC_URI[sha256sum] = "50d23afd46f7b49c4cb82a6500b0fe1fb53378af5efce95fd275ea33c879e1dd"

inherit cmake pkgconfig
