DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = "http://developer.download.nvidia.com/embedded/L4T/r21_Release_v5.0/Tegra124_Linux_R21.5.0_armhf.tbz2 \
           file://xorg.conf.add \
           file://nv \
           file://nvfb \
	   "

LIC_FILES_CHKSUM = "file://nv_tegra/LICENSE;md5=60ad17cc726658e8cf73578bea47b85f"

SRC_URI[md5sum] = "6b2df64b29eba7f643d4497a5fd454d7"
SRC_URI[sha256sum] = "a639e3e37e17ef11ea5754d7b703a133febdd6a5d3dd2e5b3924288930a45cca"

PR = "r5"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}-boot ${PN}-firstboot"

INITSCRIPT_NAME_${PN}-boot = "nv"
INITSCRIPT_PARAMS_${PN}-boot = "start 41 S . "

INITSCRIPT_NAME_${PN}-firstboot = "nvfb"
INITSCRIPT_PARAMS_${PN}-firstboot = "start 40 S . "

DEPENDS = "virtual/libx11 alsa-lib libxext"

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/Linux_for_Tegra"


PACKAGES =+ "${PN}-firmware ${PN}-boot ${PN}-firstboot"

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} =  "${bindir}/* ${libdir}/* ${sysconfdir}/* ${sysconfdir}/*/*"
RRECOMMENDS_${PN} = "xserver-xorg-module-libwfb"
RDEPENDS_${PN} = "xserver-xorg bash"

FILES_${PN}-firmware = "${base_libdir}/firmware/* ${base_libdir}/firmware/tegra12x/* "

INHIBIT_PACKAGE_STRIP = "1"
#INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "dev-so"

do_patch () {
    mkdir -p ${WORKDIR}/l4tdrv
    tar xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/config.tbz2 -C ${WORKDIR}/l4tdrv
}

do_install () {
    tar xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2 -C ${D}
    ln -sf ./libcuda.so.1.1 ${D}/usr/lib/arm-linux-gnueabihf/tegra/libcuda.so
    ln -sf ./arm-linux-gnueabihf/tegra/libcuda.so ${D}/usr/lib/libcuda.so
    ln -sf ./arm-linux-gnueabihf/tegra/libGL.so.1 ${D}/usr/lib/libGL.so
    cp ${WORKDIR}/l4tdrv/etc/asound* ${D}/etc/
    cp -r ${WORKDIR}/l4tdrv/etc/udev ${D}/etc/
    mkdir ${D}/etc/X11/ 
    cp ${WORKDIR}/l4tdrv/etc/X11/xorg.conf* ${D}/etc/X11/
    cat ${WORKDIR}/l4tdrv/etc/X11/xorg.conf.jetson-tk1 ${WORKDIR}/xorg.conf.add > ${D}/etc/X11/xorg.conf.jetson-tk1
    
    # install init scripts
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/nv ${D}${sysconfdir}/init.d/nv
    install -m 0755 ${WORKDIR}/nvfb ${D}${sysconfdir}/init.d/nvfb
    install -d ${D}${sysconfdir}/nv
    touch ${D}${sysconfdir}/nv/nvfirstboot
}

do_populate_sysroot () {
    tar xjf ${WORKDIR}/Linux_for_Tegra/nv_tegra/nvidia_drivers.tbz2 -C ${WORKDIR}/sysroot-destdir/
    rm ${WORKDIR}/sysroot-destdir/usr/lib/xorg/modules/extensions/libglx.so
    mkdir ${WORKDIR}/sysroot-destdir/sysroot-providers
    touch ${WORKDIR}/sysroot-destdir/sysroot-providers/${PN}
}

# Function to add the relevant ABI dependency to drivers, which should be called# from a PACKAGEFUNC.
python add_xorg_abi_depends() {
    mlprefix = d.getVar('MLPREFIX', True) or ''
    abi = "%sxorg-abi-%s-%s" % (mlprefix, "video", "19")

    pn = d.getVar("PN", True)
    d.appendVar('RDEPENDS_' + pn, ' ' + abi)
}
PACKAGEFUNCS =+ "add_xorg_abi_depends"

FILES_${PN}-boot = " \
	${sysconfdir}/init.d/nv \
"

FILES_${PN}-firstboot = "\
	${sysconfdir}/init.d/nvfb \
	${sysconfdir}/nv/nvfirstboot \
"
