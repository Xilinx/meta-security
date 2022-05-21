DESCRIPTION = "A small image for building meta-parsec packages"

inherit core-image

IMAGE_FEATURES += "ssh-server-openssh"

IMAGE_INSTALL = "\
    packagegroup-base \
    packagegroup-core-boot \
    packagegroup-security-tpm2 \
    packagegroup-security-parsec \
    swtpm \
    os-release" 

export IMAGE_BASENAME = "security-parsec-image"

IMAGE_ROOTFS_EXTRA_SPACE = "5242880"
