#!/bin/bash

OPENJDK=22    # This is the openjdk on my machine.

jenv local $OPENJDK

javac --enable-preview --release 22 -d bin src/module-info.java src/onebrc/*.java

sudo jbang \
    --javaagent=ap-loader@jvm-profiling-tools/ap-loader=start,event=cpu,file=profile.html \
    src/onebrc/CalculateAverage_nawaman.java

sudo rm -Rf bin
