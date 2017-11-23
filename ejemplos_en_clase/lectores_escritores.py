import threading
from random import random
lectores = 0
mutex = threading.Semaphore(1)
cuarto_vacio = threading.Semaphore(1)
torniquete = threading.Semaphore(1)
from time import sleep

def escritor(id):
    print "Nace el escritor %d" % id
    torniquete.acquire()
    cuarto_vacio.acquire()
    escribe(id)
    cuarto_vacio.release()
    torniquete.release()

def lector(id):
    print "Nace el lector %d" % id
    global lectores
    torniquete.acquire()
    torniquete.release()

    mutex.acquire()
    lectores = lectores + 1
    if lectores == 1:
        cuarto_vacio.acquire()
    mutex.release()

    lee(id)

    mutex.acquire()
    lectores = lectores - 1
    if lectores == 0:
        cuarto_vacio.release()
    mutex.release()

def lanza_lectores():
    i=0
    while True:
        i = i+1
        threading.Thread(target=lector, args=[i]).start()
        sleep(0.4)

def lee(id):
    lectura = (random()*45 + 15) / 100.0
    print "El lector %d lee por %f segundos" % (id, lectura)
    sleep(lectura)

def lanza_escritores():
    i=0
    while True:
        i = i+1
        threading.Thread(target=escritor, args=[i]).start()
        sleep(3)

def escribe(id):
    print "El escritor %d echa su rollo por tanto y tanto tiempo..." % id
    sleep(5)

print "Lanzando lanzadores..."
threading.Thread(target = lanza_escritores, args=[]).start()
threading.Thread(target = lanza_lectores, args=[]).start()
