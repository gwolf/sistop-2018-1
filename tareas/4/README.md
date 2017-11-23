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
