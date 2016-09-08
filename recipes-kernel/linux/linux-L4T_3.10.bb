SECTION = "kernel"
DESCRIPTION = "Linux For Tegra R21.5.0 Kernel recipe."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KERNEL_IMAGETYPE = "zImage"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc
require recipes-kernel/linux/setup-defconfig.inc

DEFAULT_PREFERENCE = "-1"

S = "${WORKDIR}/kernel"

PR = "r21.5.0"
SRC_URI = "http://developer.download.nvidia.com/embedded/L4T/r21_Release_v5.0/source/kernel_src.tbz2 \
           file://0001-kernel-add-support-for-gcc-5.patch \
           file://0001-ARM-8158-1-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
           file://gcc5.patch \
           file://defconfig"

SRC_URI[md5sum] = "266f2159d8e4f301ac879fbc5615352d"
SRC_URI[sha256sum] = "4b03c0937087e439fe2b93c537565116057e9289ee97240a4ce7895decb6a769"

KERNEL_DEVICETREE ?= "tegra124-jetson_tk1-pm375-000-c00-00.dtb"
