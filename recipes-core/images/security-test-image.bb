DESCRIPTION = "A small image for testing meta-security packages"

require security-build-image.bb

IMAGE_FEATURES += "ssh-server-openssh"

TEST_SUITES = "ssh ping ptest apparmor clamav samhain sssd tripwire checksec smack suricata"

INSTALL_CLAMAV_CVD = "1"

IMAGE_ROOTFS_EXTRA_SPACE = "5242880"
