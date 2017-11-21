#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# Este programa resuelve el ejercicio "El Servidor Web" (#5) de la tarea #4.
import threading
from random import random
from time import sleep

class Servidor:
    def __init__(self):
        self.hilos_espera = 5
        self.hilos = []
        self.cuenta_hilos = 0

        threading.Thread(target = self.lanza_cliente).start()

        # Al inicializar, debe haber hilos_espera hilos creados
        for i in range(self.hilos_espera):
            self.crea_hilo()

        threading.Thread(target = self.monitor).start()

    def lanza_cliente(self):
        while True:
            print "Lanzando un hilo cliente..."
            while len(self.hilos_vivos()) == 0:
                print "No hay servidores disponibles... Méndigo servicio tercermundista"
                sleep(random())
            mi_servidor = self.hilos_vivos()[0]
            print "Mi servidor es un: ", mi_servidor
            self.hilos_vivos()[0]['sem'].release()
            sleep(random())

    def monitor(self):
        while True:
            vivos = self.hilos_vivos()
            if len(vivos) < 5:
                print "Hay únicamente %d hilos vivos" % len(vivos)
                print "Lanzando a un nuevo servidor desde el monitor"
                self.crea_hilo()
            sleep(0.1)

    def hilos_vivos(self):
        return list(filter(lambda x: x['status']==0, self.hilos))

    def crea_hilo(self):
        self.cuenta_hilos = self.cuenta_hilos + 1
        mi_id = self.cuenta_hilos
        s = threading.Semaphore(0)
        self.hilos.append( { 'id': mi_id, 'sem': s, 'status': 0 } )
        threading.Thread(target = self.hilo_trabajador, args=[ self.hilos[-1] ]).start()

    def hilo_trabajador(self, hilo):
        hilo['sem'].acquire()
        print "El hilo %d se pone a trabajar." % hilo['id']
        hilo['status'] = 1

Servidor()
