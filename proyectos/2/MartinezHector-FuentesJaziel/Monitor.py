#Biblotecas
import curses
import psutil
import threading
import time


#Variables
global num_hlos
global conta
global mutex
global barrera
num_hlos = 4
conta = 0
mutex = threading.Semaphore(1)
barrera= threading.Semaphore(0)

#Informacion de ultimos procesos
def procs():
	lstproc = psutil.pids()
	lstproc.reverse()


	for x in xrange(0,size):
		myscreen.addstr(10+x, 2,"%d "%lstproc[x])
		myscreen.addstr(10+x, 8,psutil.Process(lstproc[x]).name()+"         ")
		myscreen.addstr(10+x, 22,psutil.Process(lstproc[x]).status()+"  ")
		myscreen.addstr(10+x, 35,"%d   "%psutil.Process(lstproc[x]).num_threads())


#inicalizacion de la barrera
def sync():
	global conta
	mutex.acquire()
	conta=conta+1
	mutex.release()

	if conta == num_hlos:
		barrera.release()
	barrera.acquire()
	barrera.release()



#f porcentaje
def porcentaje():

#memoria usada y disponible



#interfaz
def Interfaz():
