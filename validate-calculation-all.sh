#!/bin/bash


OPENJDK=22    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac --enable-preview --release 22 -d bin src/module-info.java src/onebrc/*.java

rows=${1:-1000000000}

echo "Creating the measurements.txt."
java  -cp bin  onebrc.CreateMeasurements3 $rows

echo ""
echo "Starting Baseline calculation (it will take awhile)."
timeout 120s \
    java --enable-preview -p bin -m OneBRC/onebrc.CalculateAverage_baseline | sed 's/\([0-9]\), /\1\
/g' | sed 's/[{}]//g' | grep -vE "Time: [0-9]+" | sed 's/\([0-9]*\)\.[0-9]*/\1/g' | tee result-baseline.txt

function run() {
    timeout 120s \
    java --enable-preview -p bin -m "OneBRC/onebrc.$1" | sed 's/\([0-9]\), /\1\
/g' | sed 's/[{}]//g' | grep -vE "Time: [0-9]+" 2>&1 | sed 's/\([0-9]*\)\.[0-9]*/\1/g' | tee result-$1.txt

    echo ""
    echo "Validating the results"
    diff -u result-baseline.txt result-$1.txt
    if [ $? -eq 0 ]; then
        echo ""
        echo "$1 : All match!"
    else
        echo "$1 : Differences found!"
    fi

}

for CLZZ in $(find bin/onebrc -iname "CalculateAverage_*.class" | sed 's/bin\/onebrc\///g' | sed 's/\.class//g' | grep -v '\$'); do
    echo ""
    run "$CLZZ"
    echo ""
    echo ""
    echo ""
done

echo "All Done!"