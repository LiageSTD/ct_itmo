#!/bin/bash

echo "PID:$(pidof mod.sh)"

kill "$(pidof mod.sh)"

echo "Process killed"
