DESCRIPTION = "Security packagegroup for Poky"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGES = "\
    packagegroup-core-security \
    packagegroup-security-utils \
    packagegroup-security-scanners \
    "

RDEPENDS_packagegroup-core-security = "\
    packagegroup-security-utils \
    packagegroup-security-scanners \
    "

SUMMARY_packagegroup-security-utils = "Security utilities"
RDEPENDS_packagegroup-security-utils = "\
    nmap \
    libseccomp \
    pinentry \
    ${@bb.utils.contains("DISTRO_FEATURES", "pax", "pax-utils", "",d)} \
    "

SUMMARY_packagegroup-security-scanners = "Security scanners"
RDEPENDS_packagegroup-security-scanners = "\
    nikto \
    checksecurity \
	"

SUMMARY_packagegroup-security-audit = "Security Audit tools "
RDEPENDS_packagegroup-security-audit = " \
    buck-security \
    redhat-security \
    "

SUMMARY_packagegroup-security-hardening = "Security Hardening tools"
RDEPENDS_packagegroup-security-hardening = " \
    bastille \
    "

