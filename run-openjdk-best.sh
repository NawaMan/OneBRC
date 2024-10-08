#!/bin/bash

OPENJDK=22    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac --enable-preview --release 22 -d bin src/module-info.java src/onebrc/*.java

time -p \
java --enable-preview -p bin -m OneBRC/onebrc.CalculateAverage_thomaswue_best
