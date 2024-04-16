#!/bin/bash

corr_f="[0-9]{4}-[0-1][0-9]-[0-3][0-9]"
last_date=$(ls $HOME | grep -E "^Backup-${corr_f}$" | sort | tail -n 1 | awk -F - '{print $2"-"$3"-"$4}')

if [[ $last_date == "--" ]]; then
  echo "У Вас нет бэкапов."
  exit 1
fi

if [[ ! -d $HOME/restore ]]; then
  mkdir $HOME/restore
fi

files=$(for file in `ls $HOME/Backup-$last_date`; do
  if [[ $file =~ .${corr_f}$ ]]; then
    echo ${file::-11}
  else
    echo $file
  fi
done | sort | uniq)

for file in $files; do
  name=$(ls $HOME/Backup-$last_date | grep "$file" | sort | tail -n 1)
  cp $HOME/Backup-$last_date/$name $HOME/restore/$file
done
