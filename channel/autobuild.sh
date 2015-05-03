#!/bin/sh

baseDir=$PWD;

if [-d packages]; then
rm -rf packages;
fi

mkdir packages;

cat channels.txt | while read line
do
    echo began build channel $line;
    
    sed -i "s#[0-9]\{6\}#${line}#g" $baseDir/../src/com/datatang/client/common/config/ChannelConfig.java
    ant -f ../build.xml release;
    cd packages;
    mkdir $line;
    cd ..
    mv $baseDir/../bin/DataTang_for_android.apk $baseDir/packages/$line/DataTang_for_android_$line.apk
done
