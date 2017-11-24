#!/usr/bin/python
# *-* Encoding: utf-8
import signal, os, time, threading

s = threading.Semaphore(0)

def handler(sig, ctx):
    print "Estamos rescatando de la emergencia"
    s.release()

def concurrente(s, mi_id):
    print "iniciamos el hilo %s" % mi_id
    s.acquire()
    print "Soy el hilo %s y estoy en mi sección crítica" % mi_id
    time.sleep(0.5)
    s.release()

signal.signal(signal.SIGALRM, handler)
signal.alarm(6)
for i in range(1,10):
    threading.Thread(target=concurrente, args=[s, i]).start()
time.sleep(10)
