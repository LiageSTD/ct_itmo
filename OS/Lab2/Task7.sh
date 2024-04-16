#!/bin/bash

declare -A start_values

for pid in $(ls /proc | grep -E '^[0-9]+$'); do
    if [[ -f /proc/$pid/io ]]; then
        read_bytes=$(grep -E '^read_bytes:' /proc/$pid/io | awk '{print $2}')
        start_values["$pid"]=$read_bytes
    fi
done

sleep 5

declare -A diff_values

for pid in "${!start_values[@]}"; do
    if [[ -f /proc/$pid/io ]]; then
        read_bytes=$(grep -E '^read_bytes:' /proc/$pid/io | awk '{print $2}')
        diff=$(($read_bytes - ${start_values["$pid"]}))
        diff_values["$pid"]=$diff
    fi
done
echo "${!diff_values[@]}"
for pid in $(echo "${!diff_values[@]}" | tr ' ' '\n' | sort -n -k2 -t: -r | head -n3); do
    cmd=$(cat /proc/$pid/cmdline | tr -d '\0')
    echo "$pid:$cmd:${diff_values["$pid"]}"
done

