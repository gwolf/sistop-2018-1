import threading
import time
import random
mutex = threading.Semaphore(1)
elementos = threading.Semaphore(0)
antibarrera = threading.Semaphore(0)
buffer = []
buffer_max = 5

class Evento:
    def __init__(self, prod_id):
        self.ident = random.random()
        print "Productor %d generando evento %1.3f" % (prod_id, self.ident)
        time.sleep(self.ident)
    def process(self, cons_id):
        print "Consumidor %d procesando evento %1.3f." % (cons_id, self.ident)
        time.sleep(self.ident)

def productor(id):
    while True:
        event = Evento(id)
        if len(buffer) >= buffer_max:
            print "* * * Me detengo a ver el paisaje"
            antibarrera.acquire()
        mutex.acquire()
        print "Prod %d: Hay %d elementos en el buffer" % (id, len(buffer))
        buffer.append(event)
        mutex.release()
        elementos.release()

def consumidor(id):
    while True:
        elementos.acquire()
        mutex.acquire()
        if len(buffer) >= buffer_max:
            print "* * * Siganle colegas!"
            antibarrera.release()
        print "Cons %d: Hay %d elementos en el buffer" % (id, len(buffer))
        event = buffer.pop()
        mutex.release()
        event.process(id)

threading.Thread(target=productor, args=[1]).start()
threading.Thread(target=productor, args=[2]).start()
threading.Thread(target=productor, args=[3]).start()
threading.Thread(target=productor, args=[4]).start()
threading.Thread(target=productor, args=[5]).start()
threading.Thread(target=consumidor, args=[1]).start()

