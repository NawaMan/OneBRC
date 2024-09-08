#!/bin/bash

OPENJDK=22    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac --enable-preview --release 22 -d bin src/module-info.java src/onebrc/*.java

time -p \
java --enable-preview -Xms32g -Xmx32g -XX:+DisableExplicitGC --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -ea -p bin -m OneBRC/onebrc.CalculateAverage_nawaman
