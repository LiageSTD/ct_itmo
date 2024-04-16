#!/bin/bash

arr=()
i=0

rm -f $f

while true; do
        curr_len=$((${#arr[@]} + 10))
	if (( $curr_len > 1100000)); then
		exit 0
	fi
        arr+=(1 2 3 4 5 6 7 8 9 10)
        let i=$i+1
done
