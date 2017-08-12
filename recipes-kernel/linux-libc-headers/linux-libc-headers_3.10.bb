require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

EXTRA_OEMAKE = "-e MAKEFLAGS="
PR = "r21.5.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = " \
        http://developer.download.nvidia.com/embedded/L4T/r21_Release_v5.0/source/kernel_src.tbz2 \
        file://scripts-Makefile.headersinst-install-headers-from-sc.patch \
        file://0001-kernel-add-support-for-gcc-5.patch \
"
SRC_URI[md5sum] = "266f2159d8e4f301ac879fbc5615352d"
SRC_URI[sha256sum] = "4b03c0937087e439fe2b93c537565116057e9289ee97240a4ce7895decb6a769"

S = "${WORKDIR}/kernel"

