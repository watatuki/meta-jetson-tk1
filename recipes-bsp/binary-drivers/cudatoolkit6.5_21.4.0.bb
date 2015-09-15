DESCRIPTION = "NVIDIA CUDA 6.5 Toolkit for L4T Rel 21.x Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = "http://developer.download.nvidia.com/embedded/L4T/r21_Release_v3.0/cuda-repo-l4t-r21.3-6-5-prod_6.5-42_armhf.deb"

LIC_FILES_CHKSUM = "file://usr/share/doc/cuda-repo-l4t-r21.3-6-5-prod/copyright;md5=f87877410d6936081b0b8ddd124ca96d"

SRC_URI[md5sum] = "7652f8afcd01e5c82dc5c202a902469a"
SRC_URI[sha256sum] = "010ea3ba7b5afce62488a9c19f4854444962e4421510cb9dc39c548fc577d41e"

PR = "r0"

DEPENDS = " "

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/cuda6.5"

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} =  "${datadir}/lintian/* \
                ${base_prefix}/usr/local/cuda-6.5/* "


INHIBIT_PACKAGE_STRIP = "1"

INSANE_SKIP_${PN} += "dev-so"

python do_unpack () {
    locals = d.getVar('WORKDIR')
    d.setVar('WORKDIR', '${WORKDIR}/cuda6.5')
    d.setVar('S', '${WORKDIR}')
    bb.build.exec_func('base_do_unpack', d)
    d.setVar('WORKDIR', locals)
}

do_install () {
    cp -r ${WORKDIR}/cuda6.5/usr ${D}
    dpkg -x ${WORKDIR}/cuda6.5/var/cuda-repo-6-5-prod/cuda-cudart-6-5_6.5-42_armhf.deb ${D}
}
