#!/bin/bash

# Имя процесса, который мы мониторим
process_name="mem.sh"

# Имя файла для записи данных
output_file="memory_usage.log"

# Цикл, выполняющийся каждые 10 секунд
while true; do
    # Получаем текущее время
    current_time=$(date "+%Y-%m-%d %H:%M:%S")

    # Получаем значения параметров памяти системы
    memory_info=$(free -h | awk 'NR==2,NR==3 {print}')

    # Получаем значения параметров процесса
    process_info=$(ps aux | grep "$process_name" | grep -v grep)

    process_info2=$(ps aux | grep "mem2.sh" | grep -v grep)
    # Получаем верхние пять процессов
    top_processes=$(top -b -n 1 | awk 'NR>=8 && NR<=12 {print}')

    # Записываем данные в файл
    echo -e "$current_time\nMemory Info:\n$memory_info\nProcess Info:\n$process_info\nProcess 2 Info:\n$process_info2\nTop Processes:\n$top_processes\n" >> "$output_file"

    sleep 2
done
