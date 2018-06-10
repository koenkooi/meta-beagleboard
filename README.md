Meta-beagleboard
================================

Introduction
-------------------------

The official OpenEmbedded/Yocto BSP layer for Beagleboard.org platforms.

It is hosted on https://github.com/koenkooi/meta-beagleboard with the source repository at https://github.com/koenkooi/meta-beagleboard.git

The common-bsp layer depends on:

	URI: git://git.openembedded.org/openembedded-core
	layers: meta
	branch: master

	URI: https://git.yoctoproject.org/git/meta-ti
	layers: meta-ti
	branch: master

The meta-beagleboard-extras layer depends on:

	URI: git://git.openembedded.org/openembedded-core
	layers: meta
	branch: master

	URI: git://git.openembedded.org/meta-openembedded
	layers: meta-oe
	branch: master

Please follow the recommended setup procedures of your OE distribution. For Angstrom that is http://www.angstrom-distribution.org/building-angstrom, other distros should have similar online resources.


Contributing
-------------------------

Please use github for pull requests: https://github.com/koenkooi/meta-beagleboard/pulls

Reporting bugs
-------------------------

The github issue tracker (https://github.com/koenkooi/meta-beagleboard/issues) is being used to keep track of bugs.

Maintainers: Koen Kooi <koen@beagleboard.org>
