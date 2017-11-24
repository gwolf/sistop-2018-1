import subprocess
import time
import sys
import threading
import psutil
import tkinter


# Variable para saber el numero de nucleos que tiene nuestra computadora
num_nucleos = int(subprocess.getoutput("grep processor /proc/cpuinfo | wc -l"))

# Lista de las funciones que se lanzarán en los hilos
funcionesALanzar = [modeloCPU]
func_monitor = len(funcionesALanzar)

# Mutex para proteger al contador
mutex = threading.Semaphore(1)
global cont_hilos
cont_hilos = 0

# Semáforo para señalizar que todos los hilos cumplieron con su funcion
senal = threading.Semaphore(0)

######################################################

# Función para señalizar entre hilos
def senalProcesos():
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

############################################

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
            print(modeloFiltro)
            return modeloFiltro
        # Concatenamos para guardar la primera linea caracter por caracter
        modeloFiltro += i

# Función para obtener el kernel del SO
def kernel():
    version_so = subprocess.getoutput("cat /proc/version | while read c1 c2 c3 c4; do echo $c1 $c2 $c3; done")
    print(version_so)

########################################################

# Función para saber el porcentaje de uso del cpu que está manejando el Usuario
def cpuUsuario():
    cpu_estado1 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3; do echo $c2; done")
    time.sleep(1)
    cpu_estado2 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3; do echo $c2; done")
    # Para calcular el tiempo se toman dos muestreos con un segundo de diferencia y se divide entre el número de nucleos
    cpu_uso = (int(cpu_estado2) - int(cpu_estado1)) / num_nucleos
    print(cpu_uso,"%")

# Función para saber el porcentaje de uso del cpu que está manejando el Sistema
def cpuSistema():
    cpu_estado1 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5; do echo $c4; done")
    time.sleep(1)
    cpu_estado2 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5; do echo $c4; done")
    # Para calcular el tiempo se toman dos muestreos con un segundo de diferencia y se divide entre el número de nucleos
    cpu_uso = (int(cpu_estado2) - int(cpu_estado1)) / num_nucleos
    print(cpu_uso,"%")

# Función para saber el porcentaje de uso que está Inactivo
def cpuInactivo():
    cpu_estado1 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5 c6; do echo $c5; done")
    time.sleep(1)
    cpu_estado2 = subprocess.getoutput("cat /proc/stat | grep 'cpu ' | while read c1 c2 c3 c4 c5 c6; do echo $c5; done")
    # Para calcular el tiempo se toman dos muestreos con un segundo de diferencia y se divide entre el número de nucleos
    cpu_uso = (int(cpu_estado2) - int(cpu_estado1)) / num_nucleos
    print(cpu_uso,"%")

####################################################

# Función para saber la memoria total que tiene nuestra computadora, se muestra en kB
def memTotal():
    mem_total =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '1 p'")
    print(mem_total)

# Función para saber la memoria libre que tiene nuestra computadora, se muestra en kB
def memLibre():
    mem_libre =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '2 p'")
    print(mem_libre)

# Función para saber la memoria disponible que tiene nuestra computadora, se muestra en kB
def memDisponible():
    mem_disponible =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '3 p'")
    print(mem_disponible)

# Función para saber la memoria que está usando el usuario, se muestra en kB
def memUso():
    mem_uso =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '7 p'")
    print(mem_uso)

# Función para saber la memoria de intercambio total que tiene nuestra computadora, se muestra en kB
def memSwapTotal():
    mem_swap =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '19 p'")
    print(mem_swap)

# Función para saber la memoria de intercambio libre que tiene nuestra computadora, se muestra en kB
def memSwapLibre():
    mem_swaplibre =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '20 p'")
    print(mem_swaplibre)

# Función para saber la memoria de intercambio que está usando, se muestra en kB
def memSwapUso():
    mem_swapuso =  subprocess.getoutput("cat /proc/meminfo | while read c1 c2; do echo $c2; done | sed -n '6 p'")
    print(mem_swapuso)

###############################################

# Función para saber el número de procesos que tenemos
def numProcesos():
    num_procesos = subprocess.getoutput("cat /proc/loadavg | grep -o '/[0-9]*'")
    # Filtramos la información que nos sirve
    num_procesos = num_procesos[1:]
    print(num_procesos)

# Función para el número de procesos que estén ejecutandose en este momento
def numProcEjecucion():
    num_procesos = subprocess.getoutput("cat /proc/loadavg | grep -o '[0-9]*/'")
    # Filtramos la información que nos sirve
    num_procesos = num_procesos[:-1]
    print(num_procesos)

##################################################

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
    print(t_funcionamiento)

# Función para el tiempo que ha estado inactivo el sistema
def tInactivo():
    t_inactivo = subprocess.getoutput("cat /proc/uptime | while read c1 c2; do echo $c2; done")
    # Filtramos el tiempo ignorando a partir del punto decimal(los últimos 3 digitos), lo convertimos en entero
    t_inactivo = int(t_inactivo[:-3])
    # Transformamos los segundos en un formato más presentable
    t_inactivo = horaCompleta(t_inactivo)
    print(t_inactivo)

######################################################

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
    for i in range(len(username)):
        print("username:",username[i],"\tpid:",pid[i],"\tname:",nombre[i],"\tstatus:",status[i]+"\n")

###############################################################################

# Función para lanzar los hilos, las variables que se usan están declaradas al principio del programa
def iniciaHilos(arg):
    for i in funcionesALanzar:
        threading.Thread(target = i,args = []).start()

###############################################################

modeloCPU()
kernel()
cpuUsuario()
cpuSistema()
cpuInactivo()
memTotal()
memLibre()
memDisponible()
memUso()
memSwapTotal()
memSwapLibre()
memSwapUso()
numProcesos()
numProcEjecucion()
tFuncionamiento()
tInactivo()
listaProc()
