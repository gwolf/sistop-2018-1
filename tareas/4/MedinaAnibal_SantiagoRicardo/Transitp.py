from Accion import Accion
from threading import Thread,Semaphore
import threading
import time

import random
move=Accion()
class Car:
	#definimos un metodo que nos dara aleatoriamente la cantidad de autos que transitaran por la calle
	#la cantidad de hilos es la cantidad de coches que estan transitando
	def Autos(self):
		autos_cantidad=[1,2,3,4,5,6,7,8,9,10]
		i=random.choice(autos_cantidad)
		threads=list()
		n=0
		while n<i:
			hilo=threading.Thread(target=move.Actions())
			threads.append(hilo)
			hilo.start()
			n=n+1
machineCar= Car()
semaforo = Semaphore(1)
class Transito:
	#definimos un metodo que representara el transito en cada calle y lo coordinamos con los semaforos
	def Cruze(self):
		j=1
		limit=0
		#le damos un limite de transito total de 50 coches
		while limit<50:
			while j<4:
				if j==0:
					print("Pueden avanzar los de la calle A\n")
					semaforo.acquire()
          				machineCar.Autos()
					time.sleep( 3 )
          				semaforo.release()
          				j=j+1
          			if j==1:
          				print("Pueden avanzar los de la calle B\n")
          				semaforo.acquire()
          				machineCar.Autos()
					time.sleep( 3 )
          				semaforo.release()
          				j=j+1
          			if j==2:
          				print("Pueden avanzar los de la calle C\n")
          				semaforo.acquire()
          				machineCar.Autos()
					time.sleep( 3 )
          				semaforo.release()
          				j=j+1
          			if j==3:
          				print("Pueden avanzar los de la calle D\n")
          				semaforo.acquire()
          				machineCar.Autos()
					time.sleep( 3 )
          				semaforo.release()
          				j=j+1
          		j=0
          		limit=limit+1
		



	 		
