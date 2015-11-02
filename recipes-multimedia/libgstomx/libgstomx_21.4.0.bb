DESCRIPTION = "NVIDIA Linux Driver Packages"
HOMEPAGE = "https://developer.nvidia.com/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = " http://developer.download.nvidia.com/embedded/L4T/r21_Release_v4.0/source/gstomx1_src.tbz2 "

SRC_URI[md5sum] = "727f119a1e0489246eb7b98394a5d072"
SRC_URI[sha256sum] = "bfc2d228326ceebecdfcd646a45f7bd63b4295da35a536121e8b18aa6dbb0771"

PR = "r4"

DEPENDS += " gstegl libffi glib-2.0 gstreamer1.0 gstreamer1.0-plugins-base libpcre libxml2 zlib "
DEPENDS += " virtual/egl virtual/mesa virtual/libgles2 wayland gdbm drm "

S = "${WORKDIR}/gstomx1_src/gst-omx1"

inherit autotools pkgconfig 

EXTRA_OECONF = " --with-omx-target=tegra --disable-static-plugins"

CXXFLAGS += " -I${S}/omx/openmax "
CFLAGS += " -I${S}/omx/openmax "

do_configure_prepend() {
	export NOCONFIGURE="true"
	export GST_EGL_LIBS="-lgstegl-1.0 -lEGL -lX11 -lgstreamer-1.0 -lgobject-2.0 -lglib-2.0"
	export GST_EGL_CFLAGS="-I${STAGING_INCDIR}/libdrm "
#	export GST_EGL_CFLAGS="-pthread -I${STAGING_INCDIR}/gstreamer-1.0 -I${STAGING_INCDIR}/libdrm -I${STAGING_INCDIR}/glib-2.0 -I${STAGING_LIBDIR}/glib-2.0/include"
}

FILES_${PN}-dbg = " \
	/usr/src/debug/* \
	/usr/lib/gstreamer-1.0/.debug/* \
	/usr/lib/.debug/* \
"

FILES_${PN} = " \
	/usr/lib/gstreamer-1.0/libgstomx.la \
	/usr/lib/gstreamer-1.0/libgstomx.so \
	"
