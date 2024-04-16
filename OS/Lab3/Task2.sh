#!/bin/bash

at now+2minute -f "Task1.sh"
tail -n 0 -f ~/report.log &
