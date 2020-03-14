#!/bin/sh

for item in `find $1  -name "*.java"`
do
   grep -i /R.string.[^]/ ${item}
done

