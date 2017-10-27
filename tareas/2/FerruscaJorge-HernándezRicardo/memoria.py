import random

def comprimir():
	aux = -1 #para empezar en el indice con 0 cuando entre al for
	for x in range(0,30):
		aux += 1
		if memoria[aux] == '-':
			memoria.pop(aux)  	# sacamos el guión
			memoria.append('-') # agregamos	el guión que sacamos para que la memoria se mantenga con el mismo tamaño
			aux -=1    # para arreglar el indice que se modificó por el pop()

def contador_guiones():
	contador = 0
	global guiones_por_bloque # global para evitar el retorno y manejar la variable durante todo el programa
	guiones_por_bloque = [] # una lista de tuplas, en la primera posicíón -> index , segunda posición -> cantidad
	for i in range(0,30):
		if memoria[i] == '-':
			contador += 1 # contador para saber cuántos guiones hay
			if i == 29: # para asegurar que si el último espacio es '-' lo guardé en la lista
				guiones_por_bloque.append((i-contador+1, contador))
		elif contador != 0: 
			guiones_por_bloque.append((i-contador, contador))
			contador = 0

def insertar(num_proceso,new_proceso,indice):
	for i in range(num_proceso):
		memoria[indice] = new_proceso[:1] # insertamos solo una letra
		indice += 1

def indice_para_insertar():
	for i in range(len(guiones_por_bloque)):
		if num_proceso <= guiones_por_bloque[i][1]:
			return guiones_por_bloque[i][0] # retorna el indice del primer espacio donde pueda caber el proceso
	return None # retorna None cuando no hay ningun espacio donde quepa el proceso

def liberar_proceso(proceso):
	for i in range(0,30): # recorremos la memoria 
		if memoria[i] == proceso:
			memoria.pop(i)
			memoria.insert(i, '-') # cambiamos el proceso por un '-'
		

memoria = ['A','A','B','B','B','B','-','-','-','-','D','D','D','D','D','D','D','E','E','E','-','-','-','H','H','H','I','I','-','-'] #memoria inicial
num_aleatorio = random.randint(0, 4) # numero aleatorio(dependiente del for) para la liberación de un proceso (en este caso será 'D')

for x in range(0,5): #For para repeticiones en que se hará el llenado de memoria
		
	print('=================================================================') #Para diferenciar las iteraciones
	print('=================================================================')

	if x == num_aleatorio: # en la iteración 'n' se liberará el proceso 'x' (x puede ser cualquier proceso que tengas en memoria)
		liberar_proceso('D')
		print('\nEl proceso D ha sido liberado...')

	print("\nAsignación actual:\n", memoria,'\n')

	new_proceso = input('Ingresa el proceso: ')
	num_proceso = len(new_proceso) 

	print("Nuevo proceso (",new_proceso[:1],") : ", num_proceso,"\n")

	contador_guiones()
	indice = indice_para_insertar()
	if indice == None: #no hay espacio para meter proceso, comprimimos
		comprimir()
		print('*Compactación requerida*\n')
		contador_guiones()
		indice = indice_para_insertar()
		if indice == None: #no hay espacio para proceso después de comprimir
			print ("Espacio insuficiente para proceso\n")
		else:
			insertar(num_proceso, new_proceso, indice)
			print('Nueva Asignación:\n',memoria, '\n')
	else:
		insertar(num_proceso, new_proceso, indice)
		print('Nueva Asignación:\n',memoria, '\n')


