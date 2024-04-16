#!/bin/bash

users=$(ps -eo user= | sort -u)

for user in $users; do
    last_script=$(ps -u "$user" -o pid,cmd --sort=-start_time | grep -m 1 -E '\s\bbash\b')
    if [ -n "$last_script" ]; then
        echo "Пользователь: $user, Последний запущенный скрипт: $last_script"
    else
        echo "Пользователь: $user, Нет запущенных скриптов"
    fi
done
