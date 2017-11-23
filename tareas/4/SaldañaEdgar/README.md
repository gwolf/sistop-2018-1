# El cruce del río #
## Especificaciones técnicas ##

Para compilarlo se deberá escribir en la terminal:

`$ javac *.java`

Y después 

`$ java Cruce`

## Solución del problema ##
Se lanzó una cantidad arbitraría (26) de hilos Persona, los cuales caerán en alguna de las siguientes tres situaciones:

1. Posibilidad de abordar: el hilo modifica el arreglo _balsa_ con la identidad que tenga asignada (hacker o serf) y finaliza su ejecución.
2. Balsa llena: el hilo solamente imprime un mensaje indicando que la balsa ya está llena, los integrantes de la balsa, y finaliza su ejecución.
3. Imposibilidad de abordar por restricciones de seguridad: si el hilo llegara a abordar provocaría una balsa con 3 integrantes de un grupo y solo 1 del otro grupo, por lo cual el hilo no hace nada y finaliza su ejecución.

En cualquier caso se utilizó un mutex implementado con un semáforo para evitar el atropellamiento entre los hilos.