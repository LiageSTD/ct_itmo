#!/bin/bash

for ((i=1; i<=30; i++)); do
	bash "newmem.sh" &
done
