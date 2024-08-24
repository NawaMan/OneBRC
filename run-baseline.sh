#!/bin/sh
#
#  Copyright 2023 The original authors
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

OPENJDK=21    # This is the openjdk on my machine.

jenv local $OPENJDK

javac -version
java  -version

rm -Rf bin

javac --enable-preview --release 21 -d bin src/module-info.java src/onebrc/*.java

time -p \
java  -cp bin  onebrc.CalculateAverage_baseline

