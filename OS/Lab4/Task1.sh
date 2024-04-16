#!/bin/bash

if [[ $# -ne 1 ]]; then
	echo "Должен быть только 1 аргумент, а не $#."
	exit 1
fi
if [[ ! -f $1 ]]; then
	echo "Аргумент должен быть файлом."
	exit 1
fi
if [[ ! -d $HOME/.trash ]]; then
	echo "Создаю каталог trash"
	mkdir $HOME/.trash
fi
data=$(date +"%d_%m_%y_")

newname="$data$1"
echo "$newname"
ln $1 "$HOME/.trash/$newname"

rm $1

dirToAdd=$HOME/.trash.log

if [ ! -e $HOME/.trash.log ];
then
	touch $dirToAdd
fi

echo "$PWD/$1:$newname" >> $dirToAdd

