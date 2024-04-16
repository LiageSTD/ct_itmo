#!/bin/bash

num=$(( (RANDOM % 100) + 1 ))

at=0
(tail -f pipe) | while true
do
  read line
  at=$((at + 1))
  if [ $line == "!" ]; then
	echo "Ops... Num was $num"
	kill $(pgrep g.sh)
	exit 0
  elif [ $line -eq $num ]; then
	echo "U win in $at atmpts"
	kill $(pgrep g.sh)
	exit 0
  elif [ $line -gt $num ]; then
	echo "Number is smaller"
  else
	echo "Number is greater"
  fi
done
