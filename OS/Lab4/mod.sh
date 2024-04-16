#!/bin/bash

if [[ $# -ne 1 ]]; then
        echo "Должен быть только 1 аргумент, а не $#."
        exit 1
fi
if [[ ! -f $1 ]]; then
        echo "Аргумент должен быть файлом."
        exit 1
fi

dist="$HOME/temp"

dist_f="$dist/$1"

if [ ! -d "$dist" ]; then
	mkdir -p "$dist"
fi

rsync --partial --progress --append "$1" "$dist"
