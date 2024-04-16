#!/bin/bash
count=0

target=$(tail -f pipe | read)

echo "Target: $target"

function eq() {
	count=$((count + 1))
	echo "Поздравляю! Чтобы угадать $target, тебе потребовалось $count попыток)" 
	exit 0
}
function less() {
	count=$((count + 1))
	echo "Пупупу( А чиселко-то меньше :( "
}
function more() {
	count=$((count + 1))
	echo "Мдемс( Чиселко-то больше :("
}
function gu() {
	count=$((count + 1))
	echo "Ну вот... А чиселко-то было $1"
	exit 0
}

trap 'eq' SIGTERM
trap 'less' USR1
trap 'more' USR2
trap 'gu' SIGUSR1

while true; do
	wait 1
done
