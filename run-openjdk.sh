#!/bin/bash

OPENJDK=21    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac -d bin  src/onebrc/CalculateAverage_NawaMan.java src/module-info.java

time \
java  -cp bin  onebrc.CalculateAverage_NawaMan --untimed

rm -Rf bin
