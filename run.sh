#! /bin/sh

MY_DIR=$(dirname $(readlink -f $0))

java -cp "$MY_DIR/build/dist/lib/*" ee.ioc.phon.g2p.G2P "$@"
