require recipes-graphics/xorg-xserver/xserver-xorg.inc

SRC_URI[md5sum] = "1509a9daae713895e7f5bcba8bcc05b2"
SRC_URI[sha256sum] = "0c4b45c116a812a996eb432d8508cf26c2ec8c3916ff2a50781796882f8d6457"

# These extensions are now integrated into the server, so declare the migration
# path for in-place upgrades.

RREPLACES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RPROVIDES_${PN} =  "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
RCONFLICTS_${PN} = "${PN}-extension-dri \
                    ${PN}-extension-dri2 \
                    ${PN}-extension-record \
                    ${PN}-extension-extmod \
                    ${PN}-extension-dbe \
                   "
# provided by xf86-input-evdev_2.10.0
do_install_append () {
    rm -f ${D}/usr/share/X11/xorg.conf.d/10-evdev.conf
}

# override setting from xserver-xorg.inc which only applies to version 1.18.
#| checking for PIXMAN... yes
#| checking for SYSTEMD_DAEMON... no
#| configure: error: systemd support requested but no library has been found
#| ERROR: Function failed: do_configure (log file is located at /build/krm/oe-core_V2.6.2/build/tmp-glibc/work/armv7at2hf-neon-angstrom-linux-gnueabi/xserver-xorg/2_1.17.2-r0/temp/log.do_configure.2158)

PACKAGECONFIG ?= "dri2 udev ${XORG_CRYPTO} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'dri glx', '', d)} \
                   ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "xwayland", "", d)} \
"
DEPENDS_append = " xfont "
