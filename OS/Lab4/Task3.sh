#!/bin/bash

preday=$(date -d "-7days" +"%F")
lstUPD=$(ls $HOME | grep -E "Backup-[0-9]{4}-[0-9]{2}-[0-9]{2}" | tail -1)

if [[ ! -e $HOME/backup-report ]]; then
                touch $HOME/backup-report
fi

if [[ $lstUPD != "" ]]; then
	lstUPDdate=$(date --date=$(echo $lstUPD | sed "s/^Backup-//") +"%F")
	if [[ $lstUPDdate < $preday ]]; then
		lstUPD=$HOME/Backup-$(date +"%F")
		echo "Создаётся новый бэкап"
		echo Backup-$(date +"%F") >> $HOME/backup-report
		mkdir $lstUPD
	else
		lstUPD=$HOME/$lstUPD
	fi
else
	lstUPD=$HOME/Backup-$(date +"%F")
	mkdir $lstUPD
fi
for name in $(ls "$HOME/source")
do
	if [[ -e $lstUPD/$name && $(stat $lstUPD/$name -c%s) -ne $(stat $HOME/source/$name -c%s) ]]; then
		cp $HOME/source/$name $lstUPD/$name.$(date +"%F")
		echo $name.$(date +"%F") >> $HOME/backup-report
	else
		cp $HOME/source/$name $lstUPD/$name
		echo $name >> $HOME/backup-report
	fi
done
