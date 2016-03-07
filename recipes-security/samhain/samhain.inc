DESCRIPTION = "Provides file integrity checking and log file monitoring/analysis"
HOMEPAGE    = "http://www.la-samhna.de/samhain/"
LICENSE     = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ca43cbc842c2336e835926c2166c28b"


SRC_URI = "http://la-samhna.de/archive/samhain_signed-${PV}.tar.gz \
	   file://${INITSCRIPT_NAME}.init \
	   file://${INITSCRIPT_NAME}.default \
	  "

SRC_URI[md5sum] = "9112612fe569a0c92c7479ed2ef3b6f0"
SRC_URI[sha256sum] = "bb32321b3dc8450351d99e90d25182a029bef1e46b9a0439ade1ca0a744eadb8"

S = "${WORKDIR}/samhain-${PV}"

inherit autotools-brokensep update-rc.d pkgconfig

SAMHAIN_PORT ??= "49777"
SAMHAIN_SERVER ??= "NULL"

INITSCRIPT_NAME = "samhain-${SAMHAIN_MODE}"
INITSCRIPT_PARAMS ?= "defaults"


PACKAGECONFIG ??= ""

# We have to unpack the tar ball twice to get to the source.
# Also as soon as OE gets the tar ball it unpacks and
# proceeds to apply the patches. But what you still have after
# the first unpack is another tar ball. So we do a do_unpack_extra()
# and tell OE to do the second unpack before do_patch(), otherwise
# do_patch() will fail when trying to apply the patches.
do_unpack_extra () {
	cd ${WORKDIR}
	tar -xzvf samhain-${PV}.tar.gz
}
addtask unpack_extra after do_unpack before do_patch

do_configure_prepend_arm() {
    export sh_cv___va_copy=yes
}

do_configure_prepend_aarch64() {
    export sh_cv___va_copy=yes
}

# If we use oe_runconf in do_configure() it will by default
# use the prefix --oldincludedir=/usr/include which is not
# recognized by Samhain's configure script and would invariably
# throw back the error "unrecognized option: --oldincludedir=/usr/include"
do_configure () {
    cat << EOF > ./config-site.${BP}
ssp_cv_lib=no
sh_cv_va_copy=yes
EOF

    export CONFIG_SITE=./config-site.${BP}
	./configure \
	    --build=${BUILD_SYS} \
	    --host=${HOST_SYS} \
	    --target=${TARGET_SYS} \
	    --prefix=${prefix} \
	    --exec_prefix=${exec_prefix} \
	    --bindir=${bindir} \
	    --sbindir=${sbindir} \
	    --libexecdir=${libexecdir} \
	    --datadir=${datadir} \
	    --sysconfdir=${sysconfdir} \
	    --sharedstatedir=${sharedstatedir} \
	    --localstatedir=${localstatedir} \
	    --libdir=${libdir} \
	    --includedir=${includedir} \
	    --infodir=${infodir} \
	    --mandir=${mandir} \
	    ${EXTRA_OECONF}
}

# Install the init script, it's default file, and the extraneous
# documentation.
do_install_append () {
    cd ${S}
	oe_runmake install DESTDIR='${D}' INSTALL=install-boot
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/${INITSCRIPT_NAME}.init \
		${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}

	install -d ${D}${sysconfdir}/default
	install -m 755 ${WORKDIR}/${INITSCRIPT_NAME}.default \
		${D}${sysconfdir}/default/${INITSCRIPT_NAME}

	install -d ${D}${docdir}/${PN}
	cp -r docs/* ${D}${docdir}/${PN}
	cp -r scripts ${D}${docdir}/${PN}
    install -d -m 755 ${D}/var/samhain
}
