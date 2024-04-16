#!/bin/bash

f_p="studs.log"

t_g="M3233"

group_s=$(grep "$t_g" "$f_p" | awk '{sum += $5} END {print sum/NR}')

below_average=$(grep "$t_g" "$f_p" | awk -v group_s="$group_s" '$5 < group_s {count++} END {print count}')

echo "Res: $below_average"
echo "Avg: $group_s"
