FILESEXTRAPATHS_prepend := "${THISDIR}/linux:"

SRC_URI += " file://usbtouch.cfg \
             file://usbaudio.cfg \
           "

KERNEL_CONFIG_FRAGMENTS_append = " \
             ${WORKDIR}/usbtouch.cfg ${WORKDIR}/usbaudio.cfg \
"
