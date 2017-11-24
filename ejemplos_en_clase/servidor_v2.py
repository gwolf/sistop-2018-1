#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# Este programa resuelve el ejercicio "El Servidor Web" (#5) de la tarea #4.
import threading
from random import random
from time import sleep

HILOS_ESPERA = 5

ESPERANDO=0
ACTIVO=1
FINALIZADO=255

class Servidor:
    def __init__(self):
        self.hilos = []
        self.cuenta_hilos = 0
        threading.Thread(target=Monitor, args=[self]).start()
        threading.Thread(target = Clientes, args=[self]).start()

    def hilos_disponibles(self):
        return list(filter(lambda x: x.disponible(), self.hilos))

    def hilo_para_atender(self):
        return self.hilos_disponibles()[0]

    def lanza_trabajador(self):
        self.cuenta_hilos = self.cuenta_hilos + 1
        mi_id = self.cuenta_hilos
        s = threading.Semaphore(0)
        trab = Trabajador(mi_id, s)
        self.hilos.append(trab)
        trab.corre()
        return trab.mi_id

    def atiendeme(self):
        self.hilo_para_atender().atiende()

class Monitor:
    def __init__(self, servidor):
        while True:
            while len(servidor.hilos_disponibles()) < HILOS_ESPERA:
                print "M: Hay únicamente %d hilos disponibles" % len(servidor.hilos_disponibles())
                trab_id = servidor.lanza_trabajador()
                print "M: El nuevo trabajador %d inició" % trab_id
#            sleep(0.1)

class Clientes:
    def __init__(self, servidor):
        while True:
            print "C: Lanzando un hilo cliente..."
            while len(servidor.hilos_disponibles()) == 0:
                print "C: No hay servidores disponibles... Méndigo servicio tercermundista"
                sleep(random())
            servidor.atiendeme()
            sleep(0.1)

class Trabajador:
    def __init__(self, mi_id, sem, status=ESPERANDO):
        self.mi_id = mi_id
        self.sem = sem
        self.status = status

    def vivo(self):
        return self.status == ESPERANDO or self.status == self.ACTIVO

    def disponible(self):
        return self.status == ESPERANDO

    def corre(self):
        self.sem.acquire()
        self.status = ACTIVO
        print "T: El hilo %d está trabajando..." % self.mi_id
        sleep(0.1)
        print "T: El hilo %d terminó su ejecución." % self.mi_id
        self.status = FINALIZADO

    def atiende(self):
        self.sem.release()

Servidor()
