DESCRIPTION = "NVIDIA OpenCV4Tegra for L4T 21.x Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = "http://developer.download.nvidia.com/embedded/OpenCV/L4T_21.2/libopencv4tegra-repo_l4t-r21_2.4.10.1_armhf.deb"

LIC_FILES_CHKSUM = "file://usr/share/doc/libopencv4tegra-repo/copyright;md5=e80c36347db74dc7d0b65f7787523132"

SRC_URI[md5sum] = "830eb30bbf6d5dbaa43358f14fc6139b"
SRC_URI[sha256sum] = "982c53f7a0825a721fa7ea919fec7386c3800d37c6fab2b991eeba6062299870"

PR = "r0"

DEPENDS = "tiff zlib jpeg glib-2.0 python gtk+ "

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/opencv4tegra"

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} +=  "${bindir}/* ${libdir}/* ${datadir}/OpenCV/*"


INHIBIT_PACKAGE_STRIP = "1"

INSANE_SKIP_${PN} += "dev-so"

python do_unpack () {
    locals = d.getVar('WORKDIR')
    d.setVar('WORKDIR', '${WORKDIR}/opencv4tegra')
    d.setVar('S', '${WORKDIR}')
    bb.build.exec_func('base_do_unpack', d)
    d.setVar('WORKDIR', locals)
}

do_install () {
    cp -r ${WORKDIR}/opencv4tegra/usr ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/opencv4tegra-repo/libopencv4tegra_2.4.10.1_armhf.deb ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/opencv4tegra-repo/libopencv4tegra-dev_2.4.10.1_armhf.deb ${D}
    dpkg -x ${WORKDIR}/opencv4tegra/var/opencv4tegra-repo/libopencv4tegra-python_2.4.10.1_armhf.deb ${D}
}
