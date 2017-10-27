# PROYECTO 2: Asignación de Memoria
#### Integrantes:  

Ferrusca Ortíz Jorge Luis  
Hernández González Ricardo Omar

#### Lenguaje de programación: 

Python

### ¿Qué se necesita para ejecutar el programa?

1. Tener instalado python3:

2. Para ejecutar el código:
	
	python3 memoria.py


## Acerca del programa

Se hizo el manejo de la memoria como una lista, donde un lugar "vacio" en la memoria es igual a un '-'. Lo primero que hace el programa es mostrar la memoria al momento, posteriormente pedir al usuario el proceso, la entrada de preferencia debería de ser en mayúsculas y sin espacios, al terminar dar enter, y ver la asignación que se generó, en dado caso que el proceso entrante no quepa en ninguno de los espacios vacios consecutivos, se hará una compresión de la memoria, si tampoco llegará a caber ahí, se mandará un mensaje de que ya no hay suficiente memoria disponible y volverá a pedir el proceso. Esto lo hará 5 veces y en alguna de esas iteraciones(random) se liberará el espacio en memoria de un proceso, en este caso será el proceso 'D', puede ser modificado para sacar el proceso que se desee que este dentro de la memoria. 


El programa como tal tiene varias "deficiencias", la primera es que pide al usuario el proceso, el número de veces que se repetirá la secuencia de llenado de la memoria, para liberar un proceso se tiene que modificar el código al momento de mandar a llamar la función "liberar_proceso()", aunque como se puede observar esto puede ser generalizado dependiendo de si se requiere o no.





