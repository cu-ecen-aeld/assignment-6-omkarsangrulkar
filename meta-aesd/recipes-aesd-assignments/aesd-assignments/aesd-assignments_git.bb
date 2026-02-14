# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
# SRC_URI = "git://git@github.com/cu-ecen-aeld/<your assignments repo>;protocol=ssh;branch=master"

# NOTE:
#  - Use protocol=ssh per assignment instructions (requires ssh key setup).
#  - Use branch=main (your repo uses main). If your repo uses master, change it back.
#  - Add the init script file from this layer (file://aesdsocket-start-stop).
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-omkarsangrulkar.git;protocol=ssh;branch=main \
           file://aesdsocket-start-stop \
          "

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
#SRCREV = "f99b82a5d4cb2a22810104f89d4126f52f4dfaba"

# IMPORTANT:
# Set SRCREV to the commit hash in your aesd-assignments repo you want Yocto to build.
# Example:
# SRCREV = "0123456789abcdef0123456789abcdef01234567"
SRCREV = "7daac4a36251647cb7a17ac9709070b8770f2378"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
#FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesdsocket-start-stop"

# Ensure the init script gets installed and registered for runlevels
inherit update-rc.d
INITSCRIPT_NAME = "aesdsocket-start-stop"
INITSCRIPT_PARAMS = "defaults 99"

# TODO: customize these as necessary for any libraries you need for your application
# (and remove comment)
#TARGET_LDFLAGS += "-pthread -lrt"

# Your aesdsocket uses pthreads. Usually the Makefile already links pthread.
# Keeping this conservative to avoid breaking your existing build:
TARGET_LDFLAGS += "-pthread"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

	# Install the aesdsocket binary to /usr/bin
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

	# Install init script to /etc/init.d so aesdsocket starts automatically
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket-start-stop
}
