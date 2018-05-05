DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "Proprietary"

SRC_URI = "http://developer.download.nvidia.com/embedded/L4T/r21_Release_v6.0/Tegra124_Linux_R21.6.0_armhf.tbz2  \
           file://xorg.conf.add \
           file://nv \
           file://nvfb \
           file://nv.service \
           file://nvfb.service \
	   "

LIC_FILES_CHKSUM = "file://nv_tegra/LICENSE;md5=60ad17cc726658e8cf73578bea47b85f"

SRC_URI[md5sum] = "e82193b117171243ea21c908fbd347ed"
SRC_URI[sha256sum] = "b9dfbdd0d21d6454e5e9668673a3494e7567698764673938d4c1ef83f42ee72d"

PR = "r6"

inherit update-rc.d systemd

INITSCRIPT_PACKAGES = "${PN}-boot ${PN}-firstboot"

INITSCRIPT_NAME_${PN}-boot = "nv"
INITSCRIPT_PARAMS_${PN}-boot = "start 41 S . "

INITSCRIPT_NAME_${PN}-firstboot = "nvfb"
INITSCRIPT_PARAMS_${PN}-firstboot = "start 40 S . "

DEPENDS = "virtual/libx11 alsa-lib libxext"

INSANE_SKIP_${PN} = "ldflags"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/Linux_for_Tegra"


PACKAGES =+ "${PN}-firmware ${PN}-boot ${PN}-firstboot "

INSANE_SKIP_${PN}-dev = "ldflags"

FILES_${PN} =  "${bindir}/* ${libdir}/* ${sysconfdir}/* ${sysconfdir}/*/*"
RRECOMMENDS_${PN} = "xserver-xorg-module-libwfb"
RDEPENDS_${PN} = "xserver-xorg bash"

FILES_${PN}-firmware = "${base_libdir}/firmware/* ${base_libdir}/firmware/tegra12x/* "

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

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
    
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        # install systemd service
        install -d ${D}${sysconfdir}/systemd/
        install -d ${D}${sysconfdir}/systemd/system
        
        install -m 0755 ${WORKDIR}/nv ${D}${sysconfdir}/systemd/nv
        install -m 0755 ${WORKDIR}/nvfb ${D}${sysconfdir}/systemd/nvfb

        install -d ${D}/lib/
        install -d ${D}/lib/systemd/
        install -d ${D}/lib/systemd/system/

        install -m 0644 ${WORKDIR}/nv.service ${D}${base_libdir}/systemd/system/
        install -m 0644 ${WORKDIR}/nvfb.service ${D}${base_libdir}/systemd/system/
    else
        # install init scripts
        install -d ${D}${sysconfdir}/init.d/
        install -m 0755 ${WORKDIR}/nv ${D}${sysconfdir}/init.d/nv
        install -m 0755 ${WORKDIR}/nvfb ${D}${sysconfdir}/init.d/nvfb
    fi

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
	${sysconfdir}/systemd/nv    \
	${base_libdir}/systemd/system/nv.service \
"

FILES_${PN}-firstboot = "\
	${sysconfdir}/init.d/nvfb \
	${sysconfdir}/systemd/nvfb    \
	${base_libdir}/systemd/system/nvfb.service \
	${sysconfdir}/nv/nvfirstboot \
"

SYSTEMD_PACKAGES = "${PN}-boot ${PN}-firstboot "
SYSTEMD_SERVICE_${PN}-boot = "nv.service"
SYSTEMD_SERVICE_${PN}-firstboot = "nvfb.service"

