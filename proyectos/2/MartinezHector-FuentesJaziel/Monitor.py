#Biblotecas
import curses
import psutil
import threading
import time


#Variables

#Informacion de ultimos procesos
def procs():


#inicalizacion de la barrera
def sync():
	global contador
	mutex.acquire()
	contador=contador+1
	mutex.release()

	if contador == num_hlos:
		barrera.release()
	barrera.acquire()
	barrera.release()



#f porcentaje
def porcentaje():

#memoria usada y disponible
def mem_usage():
	myscreen.addstr(7,45,sizeof_fmt(psutil.virtual_memory().available))
	myscreen.addstr(7,71,sizeof_fmt(psutil.virtual_memory().used))
	
	sync()


#interfaz
def Interfaz():
