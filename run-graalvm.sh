#!/bin/bash

GRAALVM=21.0.4    # This is the graalvm on my machine

jenv local $GRAALVM

javac        --version
native-image --version

rm -Rf graalvm-bin

javac        -d  graalvm-bin src/onebrc/CalculateAverage_NawaMan.java src/module-info.java
native-image -cp graalvm-bin onebrc.CalculateAverage_NawaMan

time \
./onebrc.calculateaverage_nawaman

rm -Rf graalvm-bin
