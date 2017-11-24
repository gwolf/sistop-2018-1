#!/usr/bin/python
# *-* Encoding: utf-8
import signal, os, time

puerto = None

def handler(signum, frame):
    print "El manejador de señales responde a la señal: ", signum

def recarga_conf(signum, frame):
    carga_conf()

def carga_conf():
    global puerto
    f = open('/tmp/conf')
    puerto = f.read()

signal.signal(signal.SIGQUIT, handler)
signal.signal(signal.SIGUSR1, recarga_conf)

carga_conf()

pid = os.getpid()
veces=0
while True:
    veces = veces + 1
    if veces % 10 == 0:
        os.kill(pid, signal.SIGUSR1)
    global puerto
    print 'Escuchando por el puerto ', puerto
    time.sleep(1)
