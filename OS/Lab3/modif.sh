#!/bin/bash

target_number=$(( (RANDOM % 100) + 1 ))

echo "$target_number"

pid=$(pidof "modif_p.sh")

echo "$target_number" > pipe

echo "Добро пожаловать в игру 'Угадай число'!"
echo "Я загадал число от 1 до 100. Попробуйте угадать."

while true; do
    read -p "Ваш вариант: " user_guess

    if [ "$user_guess" -eq "$target_number" ]; then
	kill -SIGTERM $pid
        break
    elif [ "$user_guess" -lt "$target_number" ]; then
        kill -USR1 $pid
    elif [ "$user_guess" -gt "$target_number" ]; then
        kill -USR2 $pid
    else
        echo "Введите корректное число."
    fi
done
