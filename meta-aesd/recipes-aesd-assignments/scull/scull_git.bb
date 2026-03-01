inherit module update-rc.d

SUMMARY = "AESD scull kernel module"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-omkarsangrulkar.git;protocol=https;branch=main \
           file://scull-start-stop \
          "
SRCREV = "c66d4850ff2a63d9f58e051615301359e51b176a"
PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/scull EXTRA_CFLAGS=-I${S}/include"

FILES:${PN} += "${sysconfdir}/init.d/scull-start-stop"

INITSCRIPT_NAME = "scull-start-stop"
INITSCRIPT_PARAMS = "defaults 98"

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/scull-start-stop ${D}${sysconfdir}/init.d/scull-start-stop
}
