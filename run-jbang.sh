#!/bin/bash

OPENJDK=21    # This is the openjdk on my machine.

jenv local $OPENJDK

sudo jbang \
    --javaagent=ap-loader@jvm-profiling-tools/ap-loader=start,event=cpu,file=profile.html \
    src/onebrc/CalculateAverage_nawaman.java

sudo rm -Rf bin
