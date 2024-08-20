#!/bin/bash

OPENJDK=21    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac -d bin  src/onebrc/CalculateAverage_nawaman.java src/module-info.java

time -p \
java  -cp bin  onebrc.CalculateAverage_nawaman
