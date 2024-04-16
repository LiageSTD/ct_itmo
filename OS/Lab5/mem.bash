#!/bin/bash

f="report.log"
arr=()
i=0

rm -f $f

while true; do
	if [[ $((i % 100000)) == 0 ]]; then
		echo ${#arr[*]} >> $f
	fi
	arr+=(1 2 3 4 5 6 7 8 9 10)
	let i=$i+1
done
