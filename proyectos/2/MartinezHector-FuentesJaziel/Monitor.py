#Biblotecas
import curses
import psutil
import threading
import time
import platform

#variables
global numerosHilos
global conta
global mutex
global barrera
#Variables para la sincronizar hilos
numerosHilos = 4
conta = 0
mutex = threading.Semaphore(1)
barrera= threading.Semaphore(0)



#Conversion de bytes 
def convesion(num, sufijo='B'):
    for unit in ['','K','M','G','T','P','E','Z']:
        if abs(num) < 1024.0:
            return "%3.1f%s%s" % (num, unit, sufijo)
        num /= 1024.0
    return "%.1f%s%s" % (num, 'Yi', sufijo)

#procesos en ejecucion
def procesos(tamanio):
	lstproc = psutil.pids()
	lstproc.reverse()


	for x in xrange(0,tamanio):
		pantalla.addstr(10+x, 2,"%d "%lstproc[x])
		pantalla.addstr(10+x, 8,psutil.Process(lstproc[x]).name()+"         ")
		pantalla.addstr(10+x, 22,psutil.Process(lstproc[x]).status()+"  ")
		pantalla.addstr(10+x, 35,"%d   "%psutil.Process(lstproc[x]).num_threads())
		pantalla.addstr(10+x, 43,psutil.Process(lstproc[x]).username()+"      ")
		pantalla.addstr(10+x, 53,"%.2f"%psutil.Process(lstproc[x]).cpu_percent(interval=0))
		pantalla.addstr(10+x, 63,convesion(psutil.Process(lstproc[x]).memory_info().rss)+"    ")



#inicalizacion de la barrera
def inbarre():
	global conta
	mutex.acquire()
	conta=conta+1
	mutex.release()

	if conta == numerosHilos:
		barrera.release()
	barrera.acquire()
	barrera.release()

# porcentaje
def porcentaje (fuente,y):
	tamanio = 50
	percent = int(round(fuente/2))
	for i in range(0,percent):
		pantalla.addstr(y,i+18," ",curses.A_STANDOUT)
	for i in range(percent,tamanio):
		pantalla.addstr(y,i+18," ")
	if fuente<10:
		pantalla.addstr(y,18+tamanio,"| 0%d %%"%fuente)
	else:
		pantalla.addstr(y,18+tamanio,"| %d %%"%fuente)
	pantalla.refresh()
	
	inbarre()

# la memoria usada y disponible
def memoriaUsada():
	pantalla.addstr(7,45,convesion(psutil.virtual_memory().available))
	pantalla.addstr(7,71,convesion(psutil.virtual_memory().used))
	
	inbarre()

# inicia los hilos de las funciones 
def hilos():
	threading.Thread(target=memoriaUsada, args=[]).start()
	threading.Thread(target=procesos, args=[13]).start()
	threading.Thread(target=porcentaje, args=[psutil.cpu_percent(interval=1),2]).start()
	threading.Thread(target=porcentaje, args=[psutil.virtual_memory().percent,4]).start()
	
#Funcion que dibuja toda la interfaz
def interfaz(args):
	global pantalla
	global conta
	global numerosHilos
	
	
	
	while True:	

		pantalla = curses.initscr()
		
		
		pantalla.addstr(0, 45, "Monitor de Procesos",curses.A_BOLD)
		pantalla.addstr(2, 1, "Uso de CPU:")
		pantalla.addstr(4, 1, "Uso de MEMORIA:")
		pantalla.addstr(6, 28, platform.system())
		pantalla.addstr(6, 34, platform.processor())
		pantalla.addstr(6, 43,"%d Nucleos"%psutil.cpu_count())
		pantalla.addstr(7, 3,"Memoria Total: "+convesion(psutil.virtual_memory().total))
		pantalla.addstr(7, 30,"Memoria Libre:")
		pantalla.addstr(7, 55,"Memoria en Uso:")
		
		for j in xrange(1,79):
			pantalla.addstr(9, j, " ")
		pantalla.addstr(9, 2,"PID",curses.A_REVERSE)
		pantalla.addstr(9,8,"Nombre",curses.A_REVERSE)
		pantalla.addstr(9,23,"Estado",curses.A_REVERSE)
		pantalla.addstr(9,34,"Hilos",curses.A_REVERSE)
		pantalla.addstr(9,42,"Usuario",curses.A_REVERSE)
		pantalla.addstr(9,53,"% CPU",curses.A_REVERSE)
		pantalla.addstr(9,63,"Memoria",curses.A_REVERSE)
		
		
		hilos()
		mutex.acquire()
		conta-=numerosHilos
		mutex.release()
		
		pantalla.refresh()
		
		
		curses.endwin()

curses.wrapper(interfaz)
