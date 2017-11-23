#!/bin/sh

while /bin/true
do
    lockfile /tmp/bloqueo
    for i in 1 2 3
    do
	echo $$
	sleep 0.3
    done
    rm -f /tmp/bloqueo
    sleep 1
done
    
