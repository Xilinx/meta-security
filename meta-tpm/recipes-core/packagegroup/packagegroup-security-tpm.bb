DESCRIPTION = "Security packagegroup for Poky"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGES = "packagegroup-security-tpm"

SUMMARY_packagegroup-security-tpm = "Security TPM support"
RDEPENDS_packagegroup-security-tpm = " \
    tpm-tools \
    trousers \
    tpm2.0-tools \
    libtss2 \
    resourcemgr \
    libtctidevice \
    libtctisocket \
    resourcemgr \
    libtpm \
    swtpm \
    "

