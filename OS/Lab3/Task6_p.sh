#!/bin/bash

num=1
mode=0

function p() {
  mode=0
}

function m() {
  mode=1
}

function t() {
  echo "Ответ - $num"
  exit
}

trap 'p' USR1
trap 'm' USR2
trap 't' SIGTERM

while true
do
	case $mode in
		0)
		num=$(($num+2))
		;;
		1)
		num=$(($num*2))
		;;
	esac
	echo $num
 	sleep 1
done

