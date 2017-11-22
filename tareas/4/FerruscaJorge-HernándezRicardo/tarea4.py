# Problema: El Cruce del Río.
# Planteamiento:
# Para llegar a un encuentro de desarrolladores de sistemas operativos, hace falta cruzar un río en balsa.
# Los desarrolladores podrían pelearse entre sí, hay que cuidar que vayan con un balance adecuado.
# Reglas:
# En la balsa caben cuatro (y sólo cuatro) personas. La balsa es demasiado ligera,
# y con menos de cuatro puede volcar.
# Al encuentro están invitados hackers (desarrolladores de Linux) y serfs (desarrolladores de Microsoft).
# Para evitar peleas, debe mantenerse un buen balance: No debes permitir que aborden
# tres hackers y un serf, o tres serfs y un hacker. Pueden subir cuatro del mismo bando, o dos y dos.
# Hay sólo una balsa. No se preocupen por devolver la balsa (está programada para volver sola)

import threading
from random import random
from time import sleep

# Contadores para desarrolladores de Linux (hackers) y Microsoft (serfs)
global hackers
global serfs
# Contador para señalar numero de personas que abordan la Balsa y de qué tipo
global personasBalsa, numHackers, numSerfs
# Mutex's - semáforo inicializado en 1
mutex = threading.Semaphore(1)
proteccionBalsa = threading.Semaphore(1)
# Semáforos para señalizar el número máximo de hackers y serfs que puede haber en la balsa
filaHackers = threading.Semaphore(0)
filaSerfs = threading.Semaphore(0)
# Barrera para que con 4 pueda partir la balsa
barrera = threading.Barrier(4)


# Función para señalar que se envía la balsa con x hackers y x selfs
def cruzandoRio(desarrollador,id):
	global personasBalsa, numHackers, numSerfs
	# Ha abordado una persona
	print("Aborda el",desarrollador,id)
	personasBalsa += 1
	# Comparaciones para saber si el que abordó es hacker o serf
	if desarrollador == "hacker":
		numHackers += 1
	if desarrollador == "self":
		numSerfs += 1
	# Si ya hay 4 personas en la balsa, ésta puede partir
	if personasBalsa == 4:
		print("La balsa se ha ido con",numHackers,"hackers y con",numSerfs,"serfs")
		personasBalsa = 0
		numSerfs = 0
		numHackers = 0


# Función para saber cuando ha llegado un hacker a la balsa
def llegaHacker(idHacker):
	# Adquirimos el mutex para el contador (incrementar y comparar)
	mutex.acquire()
	global hackers
	global serfs
	# Ha ingresado un hacker
	hackers += 1
	print("Hacker",idHacker,"llegó")
	# Hay mínimo 4 hackers (Primera posibilidad: 4 del mismo bando)
	if hackers >= 4:
		# Se liberan 4 hackers de la fila para poderse subir a la balsa
		filaHackers.release()
		filaHackers.release()
		filaHackers.release()
		filaHackers.release()
		# Decrementamos el número de hackers que subió a la balsa
		hackers -= 4
		# Liberamos mutex
		mutex.release()
	# Hay 2 hackers y 2 selfs (Segunda posibilidad: dos y dos)
	elif hackers >= 2 and serfs >= 2:
		# Se liberan 2 hackers y 2 self de la fila para poderse subir a la balsa
		filaHackers.release()
		filaHackers.release()
		filaSerfs.release()
		filaSerfs.release()
		# Decrementamos el número de hackers  y selfs que subió a la balsa
		hackers -= 2
		serfs -= 2
		# Liberamos mutex
		mutex.release()
	else:
		# No se cumple ninguna posibilidad, solo se libera mutex
		mutex.release()
	# Cada hacker adquiere el semáforo señalando que está en la fila de espera
	filaHackers.acquire()
	# Si la barrera entra en "estado roto" se reinicia, solo por cualquier error
	if barrera.broken:
		barrera.reset()
	# Se queda esperando hasta que 4 hilos esten en este "wait"
	barrera.wait()
	# Mutex para proteger la función cruzandoRio
	proteccionBalsa.acquire()
	cruzandoRio("hacker",idHacker)
	proteccionBalsa.release()


# Función para saber cuando ha llegado un hacker a la balsa
def llegaSerf(idSerf):
	# Adquirimos el mutex para el contador (incrementar y comparar)
	mutex.acquire()
	global hackers
	global serfs
	# Ha ingresado un serf
	serfs += 1
	print("Serf",idSerf,"llegó")
	# Hay mínimo 4 serfs en la fila
	if serfs >= 4:
		# Se señaliza que pueden pasar los 4 serfs
		filaSerfs.release()
		filaSerfs.release()
		filaSerfs.release()
		filaSerfs.release()
		# Decrementamos el número de serfs de la fila que ya dejamos pasar
		serfs -= 4
		# Liberamos mutex
		mutex.release()
	# Hay 2 hackers y 2 serfs en la fila
	elif hackers >= 2 and serfs >= 2:
		# Se les avisa a los 2 hackers y 2 serfs que pueden pasar a la balsa
		filaHackers.release()
		filaHackers.release()
		filaSerfs.release()
		filaSerfs.release()
		# Decrementamos el número de serfs y hackers que abordaron
		hackers -= 2
		serfs -= 2
		# Liberamos el mutex
		mutex.release()
	else:
		# No se cumple ninguna posibilidad, solo se libera mutex
		mutex.release()
	# Serf se queda esperando a abordar
	filaSerfs.acquire()
	# Si la barrera entra en "estado roto" se reinicia, solo por cualquier error
	if barrera.broken:
		barrera.reset()
	# Se queda esperando hasta que 4 hilos esten en este "wait"
	barrera.wait()
	# Mutex para proteger la función cruzandoRio
	proteccionBalsa.acquire()
	cruzandoRio("self",idSerf)
	proteccionBalsa.release()


# Empieza programa...
hackers = 0
serfs = 0
personasBalsa = 0
numHackers = 0
numSerfs = 0

idHacker = 0
idSerf = 0

# Ciclo para que cada cierto tiempo llegue un hacker o un serf
while (True):
	# 50% probabilidad de generar un hilo hacker
	if random() < 0.5:
		idHacker += 1
		threading.Thread(target=llegaHacker, args = [idHacker]).start()
		# Cada segundo se generará un hilo
		sleep(1)
	# 50% probabilidad de generar un hilo self
	else:
		idSerf += 1
		threading.Thread(target=llegaSerf, args = [idSerf]).start()
		# Cada segundo se generará un hilo
		sleep(1)
