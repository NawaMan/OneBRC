#!/bin/bash


OPENJDK=22    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac --enable-preview --release 22 -d bin src/module-info.java src/onebrc/*.java

function run() {
    time -p \
    java --enable-preview -p bin -m "OneBRC/onebrc.$1"

}

for CLZZ in $(find bin/onebrc -iname "CalculateAverage_*.class" | sed 's/bin\/onebrc\///g' | sed 's/\.class//g' | grep -v '\$'); do
    echo ""
    echo "Running the calculation $CLZZ 10 times ..."
    for i in $(seq 1 10) ; do \
        run "$CLZZ" 2>&1 \
            | grep "real "    \
            | grep -Eo '[0-9.]+' ; \
    done \
    | python3 average.py --prefix "$CLZZ: "
done

echo "All Done!"
