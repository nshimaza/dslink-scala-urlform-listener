rm -fr build
./gradlew dist
cp -a build/stage/playBinary build/stage/dslink-java-urlform-listener
cp dslink.json build/stage/dslink-java-urlform-listener/
cp dslink-java-urlform-listener build/stage/dslink-java-urlform-listener/bin/
cd build/stage
zip -r dslink-java-urlform-listener.zip dslink-java-urlform-listener
