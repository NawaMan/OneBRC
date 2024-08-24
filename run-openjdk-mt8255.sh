#!/bin/bash

OPENJDK=21    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac --enable-preview --release 21 -d bin src/module-info.java src/onebrc/*.java

time -p \
java --enable-preview -p bin -m OneBRC/onebrc.CalculateAverage_mattiz_mt8255
