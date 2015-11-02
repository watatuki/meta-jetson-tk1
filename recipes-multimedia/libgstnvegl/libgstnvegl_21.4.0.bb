DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d"

PR = "r5"

SRC_URI = " \
	http://developer.download.nvidia.com/embedded/L4T/r21_Release_v4.0/source/gstegl_src.tbz2 \
	file://0001-rename-gstegl-to-gstnvegl.patch \
"
       
SRC_URI[md5sum] = "f2639793cb78a94f5671fd834e3503fe"
SRC_URI[sha256sum] = "8fa3159e2456214c4c88551fecb42a6b682ba9682eb414e4d36c42458eec7f8b"

S = "${WORKDIR}/gstegl_src/gst-egl"

inherit autotools pkgconfig 

EXTRA_OECONF = "--disable-nls --disable-static-plugins "

DEPENDS += " libffi glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base libpcre libxml2 zlib "
DEPENDS += " virtual/egl virtual/mesa virtual/libgles2 wayland gdbm drm "

FILES_${PN}-dbg = " \
	/usr/src/debug/* \
	/usr/lib/gstreamer-1.0/.debug/* \
	/usr/lib/.debug/* \
"

FILES_${PN} = " \
	/usr/lib/gstreamer-1.0/libgstnveglglessink.so \
	/usr/lib/gstreamer-1.0/libgstnveglglessink.la \
	/usr/lib/libgstnvegl-1.0.so \
	/usr/lib/libgstnvegl-1.0.so.0 \
	/usr/lib/libgstnvegl-1.0.so.0.203.0 \
"

