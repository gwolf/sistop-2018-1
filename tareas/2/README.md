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

## Calificaciones
- **Edgar Saldaña**
  - *Lenguaje:* Java
  - *Estrategia:* La memoria es un arreglo de caracteres; el asignador
    busca suficientes caracteres '-' contiguos.
  - *Código:* [Asignador.java](./SaldañaEdgar/Asignador.java)
  - *Documentación:* [README.md](./SaldañaEdgar/README.md)
  - *Calificación:* **10**

- **Jorge Ferrusca**
  - *Entrega extemporánea* (vale ×0.8)
  - *Lenguaje:* Java
  - *Estrategia:* La memoria es una cadena de texto; se reemplazan los
    caracteres dentro de ésta. Las letras A-Z representan procesos, el
    guión indica espacio vacío.
  - *Código:* [Memoria.java](./FerruscaJorge/Memoria.java) y
    [TestMemoria.java](./FerruscaJorge/TestMemoria.java)
  - *Documentación:*
    [instrucciones.txt](./FerruscaJorge/instrucciones.txt)
  - *Comentarios:*
    - Corrección: Si (citando a tu documentación) *se basa en colocar
      el proceso en donde encuentre lugar primero* no se trata de
      *peor ajuste* (dejar el espacio contiguo máximo tras la
      asignación), sino que *primer ajuste*.
    - Tuve que corregir tu código; la línea 147 de `Memoria.java` dice
      `if(Tabla.asigna(nombre, tamanio) == 1)`, pero no hay ninguna
      clase llamada `Tabla` ⇒ Reemplacé por `if
      (Memoria.asigna(nombre, tamanio == 1)`, y funcionó
      correctamente.
    - Tus instrucciones no indican cuál es el programa primario. Ya
      que incluyes instrucciones, deberías indicar que es `TestMemoria`.
  - *Calificación:* 9 × 0.8 = **7.2**

- **Ricardo Hernández**
  - *Entrega extemporánea* (vale ×0.8)
  - *Lenguaje:* Python
  - *Estrategia:* La *memoria* es un arreglo, parte de un *prellenado*
    constante; uno de los procesos es *finalizado* en el transcurso de
    la vida del programa, que dura cinco iteraciones.
  - *Código:* [memoria.py](./HernándezRicardo/memoria.py)
  - *Documentación:* [README.md](./HernándezRicardo/README.md)
  - *Comentarios:*
	- Resulta *mucho* más sencillo leer tus comentarios si los pones
      al inicio del bloque de código al que explican que si los pones
      después de un comando. Por ejemplo, reemplazar:

			if indice == None: #no hay espacio para meter proceso, comprimimos
				comprimir()
				print('*Compactación requerida*\n')
				contador_guiones()
				indice = indice_para_insertar()
				if indice == None: #no hay espacio para proceso después de comprimir
					print ("Espacio insuficiente para proceso\n")

	  Por:

			if indice == None:
				# No hay espacio para meter proceso, comprimimos
				comprimir()
				print('*Compactación requerida*\n')
				contador_guiones()
				indice = indice_para_insertar()
				if indice == None:
					# No hay espacio para proceso después de comprimir
					print ("Espacio insuficiente para proceso\n")

	  Aumentas mucho la legibilidad de lo que escribes. Va lo mismo
      todo a lo largo del programa.
    - Cuando recibes un proceso, no verificas si ya existe en
      memoria.
    - No especificas qué tipo de ajuste estás realizando (*primer
      ajuste*). No implementas ningún otro tipo.
    - Identificas correctamente cuando hace falta *compactación*. Ojo,
      es *compactar*, no *comprimir*.
    - Te recomiendo asomarte a las funciones de manejo de listas en
      Python. ¡Son mucho más útiles de lo que imaginas! Por ejemplo,
      tu función `contador_guiones()` mide once líneas. Podrías
      reemplazarla por una sola:

			len( filter( lambda x: x=='-', memoria ) )

      La función `filter()` recibe dos parámetros: Una *función
      lambda* (que es básicamente una función anónima que *reduce* su
      entrada, entregando `True` o `False` para cada elemento). El
      segundo elemento es tu arreglo, `memoria`. Entrega una lista
      tomando únicamente los elementos para los cuales la función
      lambda resultó verdadera. Y `len()` mide la longitud de esa
      lista.
    - En `contador_guiones()`, me llama la atención que declaras la
      variable como global *para evitar el retorno*... ¡Mal estilo!
      Muy poco recomendable. Te facilita algunas cosas en programas
      cortos, pero te va a causar problemas si lo haces en un programa
      decentemente largo. Usa variables locales siempre que puedas.
  - *Calificación:* 8 × 0.8 = **6.4**

- **Ricardo Santiago y Aníbal Medina**
  - *Entrega extemporánea* (vale ×0.8)
  - *Lenguaje:* Python
  - *Estrategia:* Manejo de un arreglo de elementos donde cada uno
    indica `'-'` para página libre, ID del proceso de otro
    modo. Y... Bueno, hay varias variables de apoyo. No 
  - *Código:* [Lista.py](./MedinaAnibal_SantiagoRicardo/Lista.py)
  - *Documentación:* 
  - *Comentarios:*
    - Ni documentación ni comentario alguno; las variables tienen
      nombres que no ayudan a comprender en lo más mínimo el
      desarrollo :-(
    - No me queda claro qué es lo que indican como "mejor ajuste" o
      "peor ajuste", pero no es lo que definimos en clase.
    - Me parece que siempre que lanza una compactación (¡no es
      compresión!) se queda en un ciclo sin fin
    - Muchas veces detecta que hace falta una compactación sin que sea
      así:

			$ python Lista.py 
			proceso actual

			['-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-']
			Ingrese el proceso:
			A 
			Ingrese cantidad del proceso
			10
			peor ajuste
			proceso nuevo:

			['-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', '-', '-', '-', '-']
			desea realizar otro proceso? (s/n):
			s
			proceso actual

			['-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', '-', '-', '-', '-']
			Ingrese el proceso:
			B
			Ingrese cantidad del proceso
			10
			se realizara una compresion
    - El programa presenta varios errores lógicos; sobreescribe
      procesos habiendo espacio disponible:
	  proceso actual

			['A', 'B', 'B', 'B', 'C', 'C', 'C', 'C', '-', 'D', 'D', 'D', 'D', 'D', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-']
			Ingrese el proceso:
			E
			Ingrese cantidad del proceso
			2
			mejor ajuste

			proceso nuevo:

			['A', 'B', 'B', 'B', 'C', 'C', 'C', 'C', '-', 'E', 'E', 'D', 'D', 'D', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-']
  - *Calificación:* 7 × 0.8 = **5.6**

- **Héctor Martínez**
  - *Entrega muy extemporánea* (vale ×0.5)
  - *Lenguaje:* C
  - *Estrategia:* Maneja un arreglo de asignación de memoria y una
    matriz con las áreas libres.
  - *Código:* [Memoria.c](./MartinezHector/Memoria.c)
  - *Documentacion:*
    [documentacion.txt](./MartinezHector/documentacion.txt)
  - *Comentarios:*
	 - Complicado entender tu lógica... Me parece muy bizantina :-|
     - La asignación que haces no sigue un modelo contiguo; no me
       queda claro qué haces dividiendo / repartiendo a cada proceso
       en varios segmentos de memoria
     - ¡El resultado es interesante sin duda! Dado lo demorado que
       está, no puedo dedicarle más tiempo, pero se ve que le
       trabajaste... ¡Quisiera entender qué quisiste hacer :-Þ
     - Y la documentación... Sencillamente no es tal :( Tuve que
       revisar para entender qué contenido debía tener el archivo que
       requiere como parámetro.
     - Calificación: 7 × 0.5 = *3.5*
