#!/bin/bash

echo "Running the calculation 10 times ..."
for i in $(seq 1 10) ; do \
    ./run-openjdk-mt7894.sh 2>&1 \
        | grep "real "    \
        | grep -Eo '[0-9.]+' ; \
done \
| python3 average.py

echo "All Done!"
