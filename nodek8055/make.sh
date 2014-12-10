#!/bin/sh

./node_modules/node-gyp/bin/node-gyp.js build

if [ $? -eq 0 ]
 then sudo node example.js 
 fi
