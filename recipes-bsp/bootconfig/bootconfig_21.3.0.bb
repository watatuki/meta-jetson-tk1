DESCRIPTION = "NVIDIA Linux Bootloader config"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"
SECTION = "base"

SRC_URI = "file://jetson-tk1_extlinux.conf.sdcard"

LIC_FILES_CHKSUM = "file://jetson-tk1_extlinux.conf.sdcard;md5=feb2dc29a10be25e2b799ad47c33fca6"

S="${WORKDIR}"

PR = "r0" 

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = "${PN}"
FILES_${PN} = "/boot/extlinux/*"

do_install () {
    mkdir ${D}/boot
    mkdir ${D}/boot/extlinux
    cp ${WORKDIR}/jetson-tk1_extlinux.conf.sdcard ${D}/boot/extlinux/extlinux.conf
}
