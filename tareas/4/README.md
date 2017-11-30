# Ejercicios de sincronización

En las clases pasadas revisamos diferentes maneras de _sincronizar_
entre procesos para controlar la impredecibilidad, prestando especial
atención a los semáforos. Resolvimos un par de ejercicios, y
demostramos al mundo que no es un tema tan complejo. ¿O sí?

Es un tema que hay que poner a prueba, y más vale "sentir" cómo se
resuelven estos problemas con tiempo, antes de enfrentarnos a un
examen — O, peor aún, a código que no funciona en un ambiente de
producción :-|

Revisen las láminas tituladas
[Administración de procesos: Ejercicios de sincronización](http://gwolf.sistop.org/laminas/06b-ejercicios-sincronizacion.pdf). Presentan
siete distintos planteamientos, situaciones que pueden resolverse
utilizando primitivas de sincronización.

## ¿Qué hacer?

Revisa los escenarios planteados, y elige tu favorito. Implementa un
programa que lo resuelva, ya sea por tu cuenta o en equipos de dos
personas. Recuerda que es _fundamental_ que la solución sea
multiprocesos o multihilos, y que la parte fundamental de la solución
sea mediante mecansimos de sincronización.

### ¡No, ese no!

Al plantear esta tarea en clase, los alumnos eligieron el problema de
_el servidor Web_ para que implementemos en clase. No elijan ese.

## Entrega

Entrega el código que resuelva el problema en el repositorio Git,
siguiendo los lineamientos de nombre de directorio acostumbrados.

Es importante que incluyan un documento de texto (un PDF, un README,
lo que gustes) indicando qué problema están resolviendo y *explicando*
qué estrategia emplearon para resolverlo, qué patrones puedes
identificar en ella.

La fecha límite para entrega en tiempo es el *martes 21 de noviembre*.

# Calificaciones y comentarios

- Jorge Ferrusca y Ricardo Hernández
  - **Calificación:** 10
  - *Problema resuelto:* El cruce del río
  - *Estrategia:* Contador de *hackers* y *serfs* en espera, se les
    hace abordar de 4 en 4 usando *colas*. Además, cada balsa usa una
    *barrera* para asegurarse de que tiene cuatro pasajeros.
  - *Código:* [tarea4.py](./FerruscaJorge-HernándezRicardo/tarea4.py)
  - *Documentación:* [README.md](./FerruscaJorge-HernándezRicardo/README.md)
  - *Comentarios:*
    - Me parece que previenen un poquito *de más*. Por ejemplo, *creo*
      que la barrera es innecesaria, porque los *hackers* y *serfs*
      van subiendo a la balsa en grupos de a 4 mediante `filaHackers`
      y `filaSerfs`.. ¿O se encontraron casos en que dos grupos (8
      personas) eran liberadas de forma concurrente?
    - Pueden sacar una llamada de la condicional central de
      `llegaHacker()` y de `llegaSerf()`: Las llamadas
      `mutex.release()` en las líneas 70, 82 y 85, así como las
      mismas llamadas en las líneas 118, 130 y 133, podrían
      reemplazarse si dejaran únicamente una `mutex.release()` en las
      líneas 85 y 133 respectivamente, un nivel de indentación más
      afuera (esto es, después de cerrar el `if`. Claro, el `else`
      quedaría también vacío, por lo que se puede eliminar). Esto es
      un poco más claro, y además es más seguro (porque hay un punto
      menos a revisar en caso de tenerle que dar mantenimiento al
      programa).
    - Duplicidad de código: Las funciones `llegaHacker()` y
      `llegaSerf()` son tan parecidas que se antoja unirlas. ¿Y si
      recibiera un segundo parámetro que le indicara la identidad de
      la persona en cuestión? De ese modo, podría ser simplemente
      `llegaPersona()`, dándoles la flexibilidad de recibir también
      `fanbois` (gente de Apple) u otros bichos con los menores
      cambios posibles al código...

- Jaziel Fuentes y Héctor Martínez
  - **Calificación:** 10
  - *Problema resuelto:* Los alumnos y el asesor
  - *Estrategia:* El cubículo tiene un apagador (`cubiculo_vacio`) que
    permite que el profesor decida dormir. Los alumnos levantn la
    `mano` para plantear su pregunta (curioso — ¡Hay una sóla mano
    para todos! ☺ ) 
  - *Código:* [alumno.py](./MartinezHector-FuentesJaziel/alumno.py)
  - *Documentación:* [Documentacion.txt](MartinezHector-FuentesJaziel/Documentacion.txt)
  - *Comentarios:*
    - Curiosa manera de decidir dormir — El último alumno en salir
      explícitamente invoca la siesta mediante `Profesor()`. ¿No sería
      más correcto que la siesta del profesor fuera un hilo
      independiente, y que *compitiera* con los alumnos bloqueándose a
      la espera del cuarto con un `cubiculo_vacio.acquire()`? (Claro,
      no puedo decr que todas las acciones del profesor estarían bien
      mapeadas, porque el profesor estaría esperando a su semáforo
      mientras los alumnos preguntan...)
    - Se me hace curioso / incómodo el uso que hacen de `dicAlu`. En
      Python, las llaves de un diccionario pueden ser cualquier
      objeto, no únicamente una cadena. Esto es, ustedes hicieron
      repetidamente lo siguiente para asignaciones y verificaciones:

		    dicAlu[str(unichr(num+48))]

	  Pero podrían haber utilizado directamente el valor numérico de
      `num`: `dicAlu[num]`. No únicamente escriben menos, sino que se
      ahorran el costo de tres llamadas a función (`+`, `unichr()` y
      `str()`).

- Edgar Saldaña
  - **Calificación:** 9
  - *Problema resuelto:* El cruce del río
  - *Estrategia:* Cada una de las `Persona`s adquiere un mutex e
    intenta abordar a la balsa; la función `Cruce.aborda` puede
    permitir su ingreso si es un estado seguro o rechazar que aborde.
  - *Código:* [Cruce.java](./SaldañaEdgar/Cruce.java), [Persona.java](./SaldañaEdgar/Persona.java)
  - *Documentación:* [README.md](./SaldañaEdgar/README.md)
  - *Comentarios:*
    - Con 26 hilos lanzados, estás condenando a que siempre queden dos
      pobres desarrolladores a quedarse con las ganas de participar ☹
      - Aproximadaente una décima parte de las ejecuciones resulta que
        únicamente se armaron 4 balsas. ¿Qué le pasó a los restantes?

			$ for i in $(seq 1 100); do java Cruce | wc -l;done | sort | uniq -c
                  8 24
                 92 30
			$ for i in $(seq 1 100); do java Cruce | wc -l;done | sort | uniq -c
                 10 24
                 90 30
            $ for i in $(seq 1 100); do java Cruce | wc -l;done | sort | uniq -c
                  9 24
                 91 30
            $ for i in $(seq 1 100); do java Cruce | wc -l;done | sort | uniq -c
                 10 24
                 90 30

        Son víctimas de un `Cruce.aborda()` muy fascista ☹. Son
        rechazados, y no tienen oportunidad de subir ni siquiera si la
        siguiente balsa estaría balanceada con ellos
    - Veo que implementas a la *orientación política* del participante
      como un atributo de la `Persona`. ¡Bien!
    - Me parece curioso que a `Persona` lo implementas como una clase,
      pero a `Balsa` no. Probablemente quedaría más limpio, dejando
      que `Cruce` manejara únicamente la relación entre `Persona`s y
      `Balsa`s.
    - También me parece raro... ¿El evento que dispara el cruce de la
      balsa es lanzado por la persona que se queda abajo? Hmmmm...

## Entregas extemporáneas (× 0.8)
- Anibal Medina y Ricardo Santiago
  - **Calificación:** 10 × 0.8 = 8
  - *Problema resuelto:* Intersección de caminos
  - *Estrategia:*
  - *Código:* [camino.py](./MedinaAnibal_SantiagoRicardo/camino.py), [Transitp.py](./MedinaAnibal_SantiagoRicardo/Transitp.py) [Accion.py](./MedinaAnibal_SantiagoRicardo/Accion.py)
  - *Documentación:* [Documentacion.txt](./MedinaAnibal_SantiagoRicardo/Documentacion.txt)
  - *Comentarios:*
    - ¡Un poquito de cariño por la ortografía, por favor! "En ves",
      "cruze"... ☹
    - Probablemente el programa mejor estructurado de los que recibí
      este semestre. ¡Bien!
    - La solución que presentan es buena y completa. Claro, el
      planteamiento dice que es un cruce _sin señalamiento vial_, esto
      es, sin semáforo. Ustedes decidieron ponerlo; yo esperaba una
      solución que "implementaran" los automovilistas, no la autoridad
      ;-)
    - En la planeación vial real similar a la del problema,
      normalmente hay semáforos con turno más largo para los que van
      de frente o giran a la derecha, lo cual permitiría el avance
      simultáneo de _casi_ todos los autos de A y B o de C y D. Claro,
      esto entra en el terreno del _refinamiento 2_ planteado
