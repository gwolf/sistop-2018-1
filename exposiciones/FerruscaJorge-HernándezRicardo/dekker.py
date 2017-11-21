import threading, time

p1_puede_entrar = False
p2_puede_entrar = False
turno = 1
contador = 0


def tareas_iniciales(id):
	print("Iniciando proceso con nombre: ",id," ...")
	time.sleep(1)

def tareas_finales(id):
	print("Finalizando proceso con nombre: ",id," ...")
	time.sleep(1)

def proceso1():
	global contador, p1_puede_entrar, p2_puede_entrar, turno
	while True:
		tareas_iniciales(threading.current_thread().getName())
		p1_puede_entrar = True
		while p2_puede_entrar:
			if turno == 2:
				p1_puede_entrar = False
				while turno == 2:
					True
				p1_puede_entrar = True
		#seccion_critica()
		contador += 1
		print("sumando: contador = ",contador)
		turno = 2
		p1_puede_entrar = False
		tareas_finales(threading.current_thread().getName())

def proceso2():
	global contador, p1_puede_entrar, p2_puede_entrar, turno
	while True:
		tareas_iniciales(threading.current_thread().getName())
		p2_puede_entrar = True
		while p1_puede_entrar:
			if turno == 1:
				p2_puede_entrar = False
				while turno == 1:
					True
				p2_puede_entrar = True
		#seccion_critica()
		contador -= 1
		print("restando: contador = ",contador)
		turno = 1
		p2_puede_entrar = False
		tareas_finales(threading.current_thread().getName())


hilo1 = threading.Thread(target=proceso1,name="proc1")
hilo2 = threading.Thread(target=proceso2,name="proc2")

hilo1.start()
hilo2.start()
