#!/bin/bash

GRAALVM=21.0.4    # This is the graalvm on my machine

jenv local $GRAALVM

javac        --version
native-image --version

rm -Rf graalvm-bin

javac --enable-preview --release 22 -d bin src/module-info.java src/onebrc/*.java
native-image -cp graalvm-bin onebrc.CalculateAverage_nawaman

time -p \
./onebrc.calculateaverage_nawaman

rm -Rf graalvm-bin
