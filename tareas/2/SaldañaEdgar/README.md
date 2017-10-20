# Asignador en memoria #

Programa encargado de simular la asignación en memoria de procesos generados al azar.
Los procesos se asignan bajo el criterio del _peor ajuste_, es decir, se escriben en el mayor espacio contiguo posible, en caso de requerirlo se llevará a cabo un _compactación_.

## Especificaciones técnicas ##

Fue desarrollado en lenguaje Java y en un entorno Linux. Es un programa multiplataforma y para compilarlo y ejecutarlo bastará ejecutar desde consola:

	$javac Asignador.java
	$java Asignador

## Forma de uso ##

Al inicio, el programa mostrará la memoria vacía:

	Memoria inicializada:

	------------------------------

Para agregar un proceso se debe introducir la opción `a`, para eliminar uno la opción `s`, y para salir del programa la opción `q`:

	a
	Entra proceso (C) de tamaño 4
	FFFFFCCCC----------UU---------

	a
	**Procesos compactados**
	FFFFFCCCCUU-------------------

	Entra proceso (T) de tamaño 12
	FFFFFCCCCUUTTTTTTTTTTTT-------

	s
	Se eliminó al proceso F
	-----CCCCUUTTTTTTTTTTTT-------
