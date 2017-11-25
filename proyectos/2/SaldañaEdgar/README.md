# Monitor de Memoria del Sistema #

Este es el segundo proyecto de la materia *Sistemas Operativos*. Fue desarrollado por Edgar Saldaña Nieves durante el semestre *2018-1*.

## ¿Qué y para qué? ##
Se trata de un programa capaz de monitorear el estado de la memoria en un sistema Linux y actualizarlo automáticamente pasados algunos segundos. Podrán ser visualizadas características como el porcentaje de ocupación de la memoria o información sobre paginación.

## ¿Cómo lo hace? ##
La idea principal es simple: el programa accede al directorio /proc y en el recopila de distintos archivos los datos que va mostrando. Es importante hacer énfasis en el hecho de que para hacer esto crea varios hilos y los sincroniza. Por ejemplo, para obtener la cantidad de espacio disponible en memoria, un solo hilo se encarga de acceder al archivo necesario, lo captura y lo registra en una estructura del programa; todo esto mientras otros muchos hilos hacen la misma tarea pero con algún otro valor que el objeto necesita saber para mostrar el conjunto de datos completo en pantalla.

### Estructuras implementadas ###

#### Barrera ###
* Con lo que conlleva, es decir, un mutex y un torniquete. Se utilizó para sincronizar a los hilos de la clase *ColectorDeProceso*, de esta manera llenaron el arreglo *datos* de la clase *Coordinador*.

#### Señal ####
* Se inicializó un semáforo con un entero negativo equivalente al número de hilos colectores el cual sirve como señal para avisar que ya todos los hilos de *ColectorDeProceso* pasaron la barrera.

#### Lectores y escritores ###
* Fue muy útil la implementación lógica del problema de lectores y escritores: el objeto interfaz funciona como lector (cuando él usa los recursos nadie más debe estar escribiendo en ellos), mientras que los hilos colectores funcionan como escritores. Para esto se consultó el libro *The Little Book of Semaphores* (Allen B. Downey).

## Entorno y especificaciones técnicas ##
Este monitor fue desarrollado en lenguaje Java, utilizando el JDK versión 1.8.0_131 en entorno Linux. Deberá ser ejecutado exclusivamente en entornos Linux ya que gran parte de su funcionamiento involucra el acceso a directorios de dicho sistema operativo, además deberá tener instalado el JDK, el cual puede ser descargado desde la [página oficial](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) de Java. Para compilarlo bastará con escribir en la terminal:

`$ javac *.java`

Después solo deberás ejecutarlo:

`$ java Monitor`

## Uso ##
El monitor no admite la introducción de ningún tipo de comando, simplemente muestra información recabada del sistema. Al ejecutarlo deberá aparecer algo como esto:

    =============================================================
    |MEMORIA                                                    |
    |######################################################· 97%|
    |-----------------------------------------------------------|
    |Total: 5944444 kb                                          |
    |Libre: 192844 kb                                           |
    |Disponible: 619964 kb                                      |
    |Buffers: 52100 kb                                          |
    |Activa: 4844352 kb                                         |
    |Activa anónima: 4525608 kb                                 |
    |Inactiva anónima: 317592 kb                                |
    |Inactiva: 601148 kb                                        |
    |Páginas anónimas: 4510148 kb                               |
    |Swap total: 0 kb                                           |
    |Swap libre: 0 kb                                           |
    |Sucia: 60 kb                                               |
    =============================================================

Lo cual se estará actualizando cada 1.5 segundos.