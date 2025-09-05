#!/bin/bash

mkdir -p build/classes
mkdir -p build/docs

javac -sourcepath src/main/java -d build/classes src/main/java/*.java

jar cfe build/HeapSort.jar Main -C build/classes .

javadoc -d build/docs src/main/java/*.java

java -jar build/HeapSort.jar