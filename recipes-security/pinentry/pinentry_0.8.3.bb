DESCRIPTION = "This is a collection of simple PIN or passphrase entry dialogs which utilize the Assuan protocol as described by the aegypten project"
HOMEPAGE = "ftp://ftp.gnupg.org/gcrypt/"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
DEPENDS = "glib-2.0 "

SRC_URI = "ftp://ftp.gnupg.org/gcrypt/pinentry/${PN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "2ae681cbca0d9fb774b2c90b11ebf56c"
SRC_URI[sha256sum] = "568b0b09b50b2388a4f94d704d5bcb28718ecd4654ed1acc43ab1f97d921a0ad"

inherit autotools pkgconfig

EXTRA_OECONF +="--disable-pinentry-gtk2 --disable-pinentry-qt \
                --disable-pinentry-qt4 --without-x "

PACKAGECONFIG ??= "ncurses"

PACKAGECONFIG[ncurses] = "--enable-ncurses  --with-ncurses-include-dir=${STAGING_INCDIR}, '--disable-ncurses', ncurses"
PACKAGECONFIG[libcap] = "--with-libcap, --without-libcap, libcap"
