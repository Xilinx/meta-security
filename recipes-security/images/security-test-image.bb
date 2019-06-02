DESCRIPTION = "A small image for testing meta-security packages"

IMAGE_FEATURES += "ssh-server-openssh"

TEST_SUITES = "ssh ping ptest apparmor clamav samhain sssd tripwire checksec smack suricata"

DISTRO_FEATURES_append = " ptest"
INSTALL_CLAMAV_CVD = "1"

IMAGE_INSTALL = "\
    packagegroup-base \
    packagegroup-core-boot \
    packagegroup-core-security \
    os-release \
    clamav-cvd \
    " 


IMAGE_LINGUAS ?= " "

LICENSE = "MIT"

inherit core-image

export IMAGE_BASENAME = "security-test-image"

IMAGE_ROOTFS_EXTRA_SPACE = "5242880"
