#!/bin/bash
if [[ $# -ne 1 ]]; then
	echo "Должен быть только 1 параметр."
	exit 1
fi
for curr in $(grep -h $1 $HOME/.trash.log)
do
#	echo $curr
	path=$(echo $curr | awk -F ":" '{print $1}')
	name=$(echo $curr | awk -F ":" '{print $2}')
	if [[ "/home/liage/Lab4/$1" == $path && -e $HOME/.trash/$name ]];
	then
		echo "Найден файл - $path. Хотите его восстановить? : y/n"
		read ans
		if [[ $ans == "y" ]]
		then
			if [ -d $(dirname $path) ]
			then
				if [[ -e $path ]]
				then
					echo "Файл с таким именем уже существует. Укажите новое имя: "
					read renamed
					ln "$HOME/.trash/$name" "$(dirname $path)/$renamed"
				else
					ln $HOME/.trash/$name $path
				fi
			else
				echo "Каталог, в котором находился файл, более не существует. Файл будет восстановлен в домашнюю директорюю"
				ln $HOME/.trash/$name $HOME/$1
			fi
			rm $HOME/.trash/$name
			grep -v $name $HOME/.trash.log > $HOME/.trash.tmp
			mv $HOME/.trash.tmp $HOME/.trash.log
			echo "Файл успешно восстановлен"
			exit 0
		fi
	fi
done
echo "No files :("


