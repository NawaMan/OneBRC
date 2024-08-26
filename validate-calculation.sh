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
echo "Starting NawaMan's calculation."
java --enable-preview -p bin -m OneBRC/onebrc.CalculateAverage_nawaman --validate | sed 's/\([0-9]\), /\1\
/g' | sed 's/[{}]//g' | grep -vE "Time: [0-9]+" | tee result-nawaman.txt

echo ""
echo "Starting Baseline calculation (it will take awhile)."
java --enable-preview -p bin -m OneBRC/onebrc.CalculateAverage_baseline | sed 's/\([0-9]\), /\1\
/g' | sed 's/[{}]//g' | grep -vE "Time: [0-9]+" | tee result-baseline.txt

echo ""
echo "Validating the results"
diff -u result-baseline.txt result-nawaman.txt
if [ $? -eq 0 ]; then
    echo ""
    echo "All match!"
else
    echo "Differences found!"
    exit 1
fi
