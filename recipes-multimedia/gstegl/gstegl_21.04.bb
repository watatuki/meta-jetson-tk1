SUMMARY = "NVIDIA gstegl plugin"
DESCRIPTION = "NVIDIA gstegl plugin."
HOMEPAGE = "https://developer.nvidia.com/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d"

DEPENDS = "virtual/egl virtual/libgles2 gstreamer1.0 libpcre gstreamer1.0-plugins-base zlib "

SRC_URI = "http://developer.download.nvidia.com/embedded/L4T/r21_Release_v4.0/source/gstegl_src.tbz2 \
           file://gstegl-crossbuild-fix.patch"

SRC_URI[md5sum] = "f2639793cb78a94f5671fd834e3503fe"
SRC_URI[sha256sum] = "8fa3159e2456214c4c88551fecb42a6b682ba9682eb414e4d36c42458eec7f8b"


inherit autotools gettext pkgconfig

S = "${WORKDIR}/gstegl_src/gst-egl"

FILES_${PN} += " ${libdir}/gstreamer-1.0/* "
FILES_${PN}-dbg += " ${libdir}/gstreamer-1.0/.debug "
