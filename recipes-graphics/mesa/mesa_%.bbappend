FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "linux-driver-package "

do_install_append() {
   sed -i -e 's/Libs:/Libs: -L\$\{libdir\}\/arm-linux-gnueabihf\/tegra-egl/g' ${D}/usr/lib/pkgconfig/egl.pc
   sed -i -e 's/Libs:/Libs: -L\$\{libdir\}\/arm-linux-gnueabihf\/tegra/g' ${D}/usr/lib/pkgconfig/gl.pc
   sed -i -e 's/Libs:/Libs: -L\$\{libdir\}\/arm-linux-gnueabihf\/tegra/g' ${D}/usr/lib/pkgconfig/glesv1_cm.pc
   sed -i -e 's/Libs:/Libs: -L\$\{libdir\}\/arm-linux-gnueabihf\/tegra/g' ${D}/usr/lib/pkgconfig/glesv2.pc

}
