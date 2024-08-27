#!/bin/bash

javac -cp ../../java-advanced-2024/artifacts/info.kgeorgiy.java.advanced.implementor.jar -d ./ ../java-solutions/info/kgeorgiy/ja/ushenko/implementor/Implementor.java
echo "Java file compiled"
jar cfm Implementor.jar MANIFEST.MF ./info
echo "Jar created"
rm -r info
echo "Done"