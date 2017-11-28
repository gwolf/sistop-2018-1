from tkinter import *
from tkinter import ttk
import subprocess
import time
import sys
import threading
import psutil

# Variable para saber el numero de nucleos que tiene nuestra computadora
num_nucleos = int(subprocess.getoutput("grep processor /proc/cpuinfo | wc -l"))

# Lista de las funciones que se lanzarán en los hilos
funcionesALanzar = ["cpuUsuario","cpuSistema","cpuInactivo","memTotal","memLibre","memUso","memSwapTotal","memSwapLibre","memSwapUso","numProcesos","numProcEjecucion","tFuncionamiento","tInactivo","listaProc"]
func_monitor = len(funcionesALanzar)

# Mutex para proteger al contador
mutex = threading.Semaphore(1)
global cont_hilos
frame = False
cont_hilos = 0

# Semáforo para señalizar que todos los hilos cumplieron con su funcion
senal = threading.Semaphore(0)

####################################################################################################################

# Función para señalizar entre hilos
def avisoProc():
	global cont_hilos
	# Adquirimos el mutex para escritura y lectura de la variable contador de hilos
	mutex.acquire()
    # Un hilo ha pasado por aquí
	cont_hilos += 1
	# Si todos los hilos ya pasaron por aquí, mandan la señal para que se vuelvan a lanzar
	if cont_hilos == func_monitor:
		senal.release()
	# Se libera mutex
	mutex.release()

######################################################################################################################

# Función que nos da el modelo del CPU de la computadora donde se corra el programa
def modeloCPU():
    # Guardamos lo que haya en /proc/cpuinfo y filtramos el apartado donde diga el modelo
    modelo = subprocess.getoutput("cat /proc/cpuinfo | grep -e 'model\ name'")
    # Variable auxiliar, ya que la variable de arriba contiene el modelo por cada nucleo que tenga la computadora
    modeloFiltro = ""
    # Recorremos el String y guardamos solo la primera linea
    for i in modelo:
        if i == '\n':
            modeloFiltro = modeloFiltro.replace("model name\t: ","")
            return modeloFiltro
        # Concatenamos para guardar la primera linea caracter por caracter
        modeloFiltro += i

# Función para obtener el kernel del SO
def kernel():
    version_so = subprocess.getoutput("cat /proc/version | while read c1 c2 c3 c4; do echo $c1 $c2 $c3; done")
    return version_so

########################################################################################################################

# Función para saber el porcentaje de uso del cpu que está manejando el Usuario
def cpuUsuario():
    cpu_estado1 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3; do echo $c2; done")
    time.sleep(1)
    cpu_estado2 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3; do echo $c2; done")
    # Para calcular el tiempo se toman dos muestreos con un segundo de diferencia y se divide entre el número de nucleos
    cpu_uso = (int(cpu_estado2) - int(cpu_estado1)) / num_nucleos
    avisoProc()
    return str(cpu_uso)

# Función para saber el porcentaje de uso del cpu que está manejando el Sistema
def cpuSistema():
    cpu_estado1 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5; do echo $c4; done")
    time.sleep(1)
    cpu_estado2 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5; do echo $c4; done")
    # Para calcular el tiempo se toman dos muestreos con un segundo de diferencia y se divide entre el número de nucleos
    cpu_uso = (int(cpu_estado2) - int(cpu_estado1)) / num_nucleos
    avisoProc()
    return str(cpu_uso)

# Función para saber el porcentaje de uso que está Inactivo
def cpuInactivo():
    cpu_estado1 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5 c6; do echo $c5; done")
    time.sleep(1)
    cpu_estado2 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5 c6; do echo $c5; done")
    # Para calcular el tiempo se toman dos muestreos con un segundo de diferencia y se divide entre el número de nucleos
    cpu_uso = (int(cpu_estado2) - int(cpu_estado1)) / num_nucleos
    avisoProc()
    return str(cpu_uso)

#########################################################################################################################

# Función para saber la memoria total que tiene nuestra computadora, se muestra en kB
def memTotal():
    mem_total =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '1 p'")
    avisoProc()
    return mem_total

# Función para saber la memoria libre que tiene nuestra computadora, se muestra en kB
def memLibre():
    mem_libre =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '2 p'")
    avisoProc()
    return mem_libre

# Función para saber la memoria que está usando el usuario, se muestra en kB
def memUso():
    mem_uso =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '7 p'")
    avisoProc()
    return mem_uso

# Función para saber la memoria de intercambio total que tiene nuestra computadora, se muestra en kB
def memSwapTotal():
    mem_swap =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '19 p'")
    avisoProc()
    return mem_swap

# Función para saber la memoria de intercambio libre que tiene nuestra computadora, se muestra en kB
def memSwapLibre():
    mem_swaplibre =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '20 p'")
    avisoProc()
    return mem_swaplibre

# Función para saber la memoria de intercambio que está usando, se muestra en kB
def memSwapUso():
    mem_swapuso =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '6 p'")
    avisoProc()
    return mem_swapuso

###########################################################################################################################

# Función para saber el número de procesos que tenemos
def numProcesos():
    num_procesos = subprocess.getoutput("cat /proc/loadavg | grep -o '/[0-9]*'")
    # Filtramos la información que nos sirve
    num_procesos = num_procesos[1:]
    avisoProc()
    return num_procesos

# Función para el número de procesos que estén ejecutandose en este momento
def numProcEjecucion():
    num_procesos = subprocess.getoutput("cat /proc/loadavg | grep -o '[0-9]*/'")
    # Filtramos la información que nos sirve
    num_procesos = num_procesos[:-1]
    avisoProc()
    return num_procesos

###########################################################################################################################

# Función de ayuda para convertir los segundos en un formato de HH:MM:SS para mayor estetica al momento de mostrarlo
def horaCompleta(segundos):
    # Para saber las horas dividimos los segundos entre 3600, muy importante hacerlo con el doble diagonal, para división entera
    horas = segundos // 3600
    # Agregamos un 0 en caso de que las horas no acompleten la decena
    if horas < 10:
        horas = '0' + str(horas)
    # Para saber los minutos divimos el residuo de lo que quedó de las horas entre 60
    minutos = (segundos % 3600) // 60
    # Agregamos un 0 en caso de que los minutos no acompleten la decena
    if minutos < 10:
        minutos = '0' + str(minutos)
    # Para calcular los segundos sacamos el modulo del residuo, lo que asegura que que no será ni horas ni minutos
    segundos = (segundos % 3600) % 60
    # Agregamos un 0 en caso de que los segundos no acompleten la decena
    if segundos < 10:
        segundos = '0' + str(segundos)
    # Retornamos la hora en formato HH:MM:SS
    return str(horas) + ":" + str(minutos) + ":" + str(segundos)

# Función para el tiempo que ha estado encendido el sistema
def tFuncionamiento():
    t_funcionamiento = subprocess.getoutput("cat /proc/uptime | while read c1 c2; do echo $c1; done")
    # Filtramos el tiempo ignorando a partir del punto decimal(los últimos 3 digitos), lo convertimos en entero
    t_funcionamiento = int(t_funcionamiento[:-3])
    # Transformamos los segundos en un formato más presentable
    t_funcionamiento = horaCompleta(t_funcionamiento)
    avisoProc()
    return t_funcionamiento

# Función para el tiempo que ha estado inactivo el sistema
def tInactivo():
    t_inactivo = subprocess.getoutput("cat /proc/uptime | while read c1 c2; do echo $c2; done")
    # Filtramos el tiempo ignorando a partir del punto decimal(los últimos 3 digitos), lo convertimos en entero
    t_inactivo = int(t_inactivo[:-3])
    # Transformamos los segundos en un formato más presentable
    t_inactivo = horaCompleta(t_inactivo)
    avisoProc()
    return t_inactivo

#####################################################################################################################

# Función para listar los procesos que tengamos, hacemos uso del módulo "psutil"
def listaProc():
    # Guardamos los datos de cada proceso en listas
    username = []
    pid = []
    nombre = []
    status = []
    # process_iter nos ayuda a iterar sobre los procesos
    for proc in psutil.process_iter():
        # Cada iteración nos generará un diccionario con los datos que nos interesan de cada proceso
        pinfo = proc.as_dict(attrs=['pid', 'name', 'username','status'])
        # Recorremos ese diccionario que nos genera para guardar en las listas cada dato
        for llave,valor in pinfo.items():
            if llave == 'username':
                username.append(valor)
            elif llave == 'pid':
                pid.append(valor)
            elif llave == 'name':
                nombre.append(valor)
            elif llave == 'status':
                status.append(valor)
    avisoProc()
    return [len(username),username,pid,nombre,status]

##########################################################################################################################

funcionesALanzar = [cpuUsuario, cpuSistema,cpuInactivo, memTotal,memLibre,memUso,memSwapTotal,memSwapLibre,memSwapUso,numProcesos,numProcEjecucion,tFuncionamiento,tInactivo,listaProc]

# Función para lanzar los hilos, las variables que se usan están declaradas al principio del programa
def iniciaHilos():
    for i in funcionesALanzar:
        threading.Thread(target = i,args = []).start()

##########################################################################################################################

# Función que contiene la interfaz gráfica con tkinter
def interfaz():
	global frame
	contenedor = Tk()
	contenedor.title("Monitor")
	frame = Frame(contenedor,heigh=1000,width=1000)
	frame.pack(padx=20,pady=20)
	frame.configure(bg = "black")

	Label(frame,text="*Características",fg="red",font="Verdana 10",bg="black").place(x=0,y=0)
	Label(frame,text="Kernel:   " +  kernel(),font="Verdana 10",bg="black",fg="white").place(x=0,y=20)
	Label(frame,text="Procesador:   " + modeloCPU(),font="Verdana 10",bg="black",fg="white").place(x=0,y=40)
	Label(frame,text="-----------------------------------------------------------------------------------------------------------------------------",bg="black",fg="white").place(x=0,y=60)
	
def actualiza():
	Label(frame,text="*Memoria",font="Verdana 10",bg="black",fg="red").place(x=0,y=80)
	Label(frame,text="memoria: ",font="Verdana 10",bg="black",fg="white").place(x=0,y=100)
	Label(frame,text=memTotal() + " Total",font="Verdana 10",bg="black",fg="white").place(x=80,y=100)
	Label(frame,text=memLibre() + " Libre",font="Verdana 10",bg="black",fg="white").place(x=210,y=100)
	Label(frame,text=memUso() + " En uso",font="Verdana 10",bg="black",fg="white").place(x=340,y=100)
	Label(frame,text="swap: ",font="Verdana 10",bg="black",fg="white").place(x=0,y=120)
	Label(frame,text=memSwapTotal() + " Total",font="Verdana 10",bg="black",fg="white").place(x=80,y=120)
	Label(frame,text=memSwapLibre() + " Libre",font="Verdana 10",bg="black",fg="white").place(x=210,y=120)
	Label(frame,text=memSwapUso() + " En uso",font="Verdana 10",bg="black",fg="white").place(x=340,y=120)
	Label(frame,text="-----------------------------------------------------------------------------------------------------------------------------",bg="black",fg="white").place(x=0,y=140)
	Label(frame,text="*CPU",font="Verdana 10",bg="black",fg="red").place(x=0,y=160)
	Label(frame,text="%CPU:",font="Verdana 10",bg="black",fg="white").place(x=0,y=180)
	Label(frame,text=cpuUsuario()+"% Uso",font="Verdana 10",bg="black",fg="white").place(x=80,y=180)
	Label(frame,text=cpuSistema()+"% Sys",font="Verdana 10",bg="black",fg="white").place(x=210,y=180)
	Label(frame,text=cpuInactivo()+"% Inac",font="Verdana 10",bg="black",fg="white").place(x=340,y=180)
	Label(frame,text="Tiempo: ",font="Verdana 10",bg="black",fg="white").place(x=0,y=200)
	Label(frame,text=tFuncionamiento()+" Total",font="Verdana 10",bg="black",fg="white").place(x=140,y=200)
	Label(frame,text=tInactivo()+" Libre",font="Verdana 10",bg="black",fg="white").place(x=280,y=200)
	Label(frame,text="-----------------------------------------------------------------------------------------------------------------------------",bg="black",fg="white").place(x=0,y=220)
	procesoss = listaProc()
	Label(frame,text="*Procesos",font="Verdana 10",bg="black",fg="red").place(x=0,y=240)
	Label(frame,text="Total:",font="Verdana 10",bg="black",fg="white").place(x=0,y=260)
	Label(frame,text=len(procesoss),font="Verdana 10",bg="black",fg="white").place(x=80,y=260)
	Label(frame,text="Activos: ",font="Verdana 10",bg="black",fg="white").place(x=250,y=260)
	Label(frame,text="Username ",font="Verdana 10",bg="black",fg="cyan").place(x=0,y=290)
	Label(frame,text="PID ",font="Verdana 10",bg="black",fg="cyan").place(x=125,y=290)
	Label(frame,text="Nombre ",font="Verdana 10",bg="black",fg="cyan").place(x=250,y=290)
	Label(frame,text="Estado ",font="Verdana 10",bg="black",fg="cyan").place(x=375,y=290)
	ejey = 310
	ejex = 0
	for i in range(1,len(procesoss)):
	    ejey = 310
	    for j in range(procesoss[0]):
	        Label(frame,text=procesoss[i][j],font="Verdana 10",bg="black",fg="white").place(x=ejex,y=ejey)
	        ejey += 20
	    ejex += 125
	ttk.Button( text='Salir', command=quit).pack(side=BOTTOM)
	frame.after(2000, actualiza)

###########################################################################################################################

def funcPrincipal():
	global cont_hilos
	global frame
	while True:
		iniciaHilos()
		# Espera a que todos los hilos terminen
		print("una vez")
		senal.acquire()
		interfaz()
		actualiza()
		time.sleep(2)
		# Mutex para reiniciar contador
		mutex.acquire()
		cont_hilos -= func_monitor
		#Lo libera y continúa con su tarea (limpiar la interfaz para una nueva impresión)
		mutex.release()
		frame.mainloop()

##########################################################################################################################

# Ejecución del programa
funcPrincipal()
