# Asignación de memoria

Los modelos de _partición variable_ y _segmentación_ requieren que
el sistema operativo asigne y libere porciones de la memoria conforme
lo requiere el conjunto de procesos.

Asumamos que los procesos no pueden pedir el _ajuste_ (esto es, que
el espacio de memoria que solicitan en un inicio se mantiene durante
toda la vida del proceso).

Escriban un programa que vaya realizando esta asignación. Asuman un
sistema que tiene 30 _unidades_ de memoria; un proceso puede
especificar que requiere entre 2 y 15 unidades.

Su programa puede recibir entrada aleatoria o del operador (ustedes
deciden cómo implementarlo, ambas estrategias valen).

1. Después de cada solicitud, imprimir cómo queda el mapa de
   memoria. Por ejemplo,

	    Asignación actual:

		AABBBB----DDDDDDEEEE---HHHII--
		Nuevo proceso (J): 3
		Nueva asignación:
		AABBBBJJJ-DDDDDDEEEE---HHHII--

2. Resolver la solicitud de los procesos; si es necesario hacer una
   compactación, indíquenlo:

		AABBBBJJJ-DDDDDDEEEE---HHHII--
		Nuevo proceso (K): 5
		*Compactación requerida*
		Nueva situación:
		AABBBBJJJDDDDDDEEEEHHHII------
		Asignando a K:
		AABBBBJJJDDDDDDEEEEHHHIIKKKKK-

3. Indicar si están resolviendo las solicitudes por _peor ajuste_,
   _mejor ajuste_ o _primer ajuste_.

**Ojo** Uno de sus compañeros me hizo ver, con toda razón, que asumo
que habrá "hoyos" en la asignación (en el ejemplo anterior, hay un
hoyo donde estuvieron los procesos C, F, G). Sí, toca también
implementar que una de las operaciones sea que un proceso termine su
ejecución, liberando el espacio que ocupaba.

## Precisiones de la entrega

Esta tarea puede ser entregada _de forma individual_ o _en equipos de
dos personas_.
