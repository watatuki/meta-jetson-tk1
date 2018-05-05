require linux-libc-headers.inc

EXTRA_OEMAKE = "-e MAKEFLAGS="
PR = "r21.6.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://nv-tegra.nvidia.com/linux-3.10.git;protocol=git;branch=l4t/l4t-r21 \
           file://0001-kernel-add-support-for-gcc-5.patch \
           file://0001-ARM-8158-1-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
           file://gcc5.patch \
           file://0001-compiler-gcc-integrate-the-various-compiler-gcc-345-.patch \
           file://gcc6.patch \
           "

SRCREV = "b271e8fa67a6d9c4600274a25636cfe00fdd1b68"

S = "${WORKDIR}/git"

