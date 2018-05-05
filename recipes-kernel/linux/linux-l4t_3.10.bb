SECTION = "kernel"
DESCRIPTION = "Linux For Tegra R21.6.0 Kernel recipe."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
KERNEL_IMAGETYPE = "zImage"

inherit kernel

DEFAULT_PREFERENCE = "-1"

S = "${WORKDIR}/git"

PR = "r21.6.0"
SRC_URI = "git://nv-tegra.nvidia.com/linux-3.10.git;protocol=git;branch=l4t/l4t-r21 \
           file://0001-kernel-add-support-for-gcc-5.patch \
           file://0001-ARM-8158-1-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
           file://gcc5.patch \
           file://0001-compiler-gcc-integrate-the-various-compiler-gcc-345-.patch \
           file://gcc6.patch \
           "

SRCREV = "b271e8fa67a6d9c4600274a25636cfe00fdd1b68"

KERNEL_DEFCONFIG = "tegra12_defconfig"

do_configure_prepend() {
        install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
}

# Work around. it ported from madisongh's meta-tegra.
export KCFLAGS = "-Wno-unused-const-variable -Wno-misleading-indentation \
                  -Wno-switch-unreachable -Wno-parentheses -Wno-maybe-uninitialized \
                  -Wno-format-truncation -Wno-format-overflow -Wno-int-in-bool-context \
"
