# TAREA 4: Ejercicios de Sincronización
### Problema: El Cruce del Río
#### Integrantes:  

Ferrusca Ortíz Jorge Luis  
Hernández González Ricardo Omar

#### Lenguaje de programación:

Python

### ¿Qué se necesita para ejecutar el programa?

1. Tener instalado python 3:

2. Para ejecutar el código:

	python3 tarea4.py


### Acerca del programa

El código está comentado línea por línea para su mejor entendimiento.

El problema de resolvió con 2 mutex, uno para proteger las funciones principales y el otro como una ayuda para hacer átomica la función de cruzandoRio.
Se usaron 2 semáforos para señalizar cuando había el número necesario de hackers y de serfs para abordar la balsa.
También se ocupó una barrera, que en python 3 ya viene implementada con threading.Barrier().

La lógica fue la siguiente:
* Se utilizó un contador para hackers y uno para serfs, los cuáles iban aumentando conforme se fueran creando hilos del tipo correspondiente, se hizo la comparación según las restricciones del programa (solo podían viajar 4 del mismo bando o dos y dos), dependiendo del caso en que cayera se hicieron los release() correspondientes de los semáforos para señalar que pueden abordar, todo esto protegido con un mutex para que no haya problemas con el contador y las comparaciones.
* Posteriormente se hizo el acquire() para que se quedaran esperando ahí hasta que se juntara el número correspondiente de hackers o serfs.
* Una vez liberado, se detiene en una barrera donde se asegura que lleguen solo 4 hilos. Por último se procede a abordar la balsa, para esto se hace uso de la función cruzandoRio(), la cual recibe como argumento una cadena de qué desarrollador está abordando, después se aumenta el contador de personas que abordaron y si han abordado 4, la balsa parte. Se hace uso de comparaciones auxiliares solo para saber si abordó un hacker o abordó un serf y al final imprimir cuántos de cada uno está viajando en la balsa.

#### Nota:
La barrera puede ser opcional, la implementamos para asegurar que sí fueran 4 hilos los que aborden.
