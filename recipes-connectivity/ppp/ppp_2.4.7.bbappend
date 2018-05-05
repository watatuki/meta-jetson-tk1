FILESEXTRAPATHS_prepend := "${THISDIR}/ppp:"

# This fix refers to https://bugzilla.redhat.com/show_bug.cgi?id=292001

SRC_URI += " \
           file://fix_builderror_rppppoe.patch \
"

