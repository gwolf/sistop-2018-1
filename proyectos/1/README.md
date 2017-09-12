# (Micro) sistema de archivos

## Fecha de entrega

La fecha límite de entrega del proyecto es el jueves **28 de
septiembre** de 2017, con límite para el _pull request_ de las 23:59
hora central de México. A diferencia de tareas y prácticas, **no se
aceptan** entregas extemporáneas.

## Descripción del proyecto

El primer proyecto de desarrollo consta del desarrollo de un (micro)
sistema de archivos. Un programa que tome a un archivo y, dentro de
él, permita operaciones básicas como si fuera un sistema de
archivos. Las operaciones mínimas que debe proveer son:

- Listar el directorio
- Crear o eliminar un archivo
- Manejo básico de archivos en modos de lectura, escritura, y para
  agregar

El programa debe permitir un uso interactivo (como si fuera un shell
del sistema operativo). Pueden desarrollarlo en el lenguaje que
prefieran, pero el resultado debe ser funcional. Pueden incluir el
sistema de archivos o indicarme cómo generarlo. Cuenta _perfectamente_
como uso interactivo el ofrecerle una interfaz al sistema operativo
para que cualquier programa pueda utilizarse dentro del
(micro)sistema, por ejemplo, desarrollando un módulo de FUSE.

Basta con que representen un _directorio plano_, con soporte para un
mínimo de cuatro entradas (archivos) diferentes. Son libres de elegir
otros límites (por ejemplo, longitud de nombre de archivo, tamaño
máximo de cada archivo, etc.) pero les pido que documenten _todos_ los
límites que puedan identificar.

_No_ vale que hagan un programa que al inicio de la ejecución
descomprima un `.zip` o similar, y lo vuelva a comprimir al terminar
la ejecución. Las operaciones deben hacerse _directmente_ en el
archivo contenedor.

El proyecto se calificará según la siguiente rúbrica:

<table>
<tr><th></th><th><b>Excelente</b> (100%)</th><th><b>Bueno</b> (75%)</th><th><b>Suficiente</b> (50%)</th><th><b>Insuficiente</b> (0%)</th><th><b>Peso</b></th></tr>
<tr><th><b>Requisitos</b></th><td></td><td></td><td></td><td></td><td>20%</td></tr>
<tr><td><em>Cumplimiento</em></td><td>Se cumplen los requisitos planteados al 100% e incluso se exceden</td><td>Los requisitos planteados se cumplen a cabalidad</td><td>El proyecto se aproxima a los requisitos, sin llegar a cumplirlos por completo</td><td>El proyecto presentado no tiene suficiente relación con lo solicitado en clase</td><td></td></tr>
<tr><td><b>Proyecto</b></td><td></td><td></td><td></td><td></td><td>20%</td></tr>
<tr><td><em>Complejidad</em></td><td>El mecanismo de representación o la manera de organizarlo son novedosos, sorprendentes</td><td>La representación de los datos resulta interesante, no obvia</td><td>Los datos se representan empleando las estructuras más naturales para tal fin</td><td</td><td></td><td></td></tr>
<tr><td><em>Claridad de desarrollo</em></td><td>El código, acompañado de la documentación, resultan de clara comprensión, a pesar de cualquier nivel de complejidad que mantengan</td><td>El código es ofuscado, dificil de seguir, pero la lectura de la documentación ayuda a hacerlo</td><td>Es dificil comprender la forma en que se desarrolló incluso teniendo la documentación, o esta está incompleta y no cubre este aspecto</td><td>Imposible de comprender</td><td></td></tr>
<tr><td><em>Interfaz usuario</em></td><td>Logra una interfaz usuario atractiva, consistente y clara, con la explicación necesaria para un uso natural y fluido. Nota: Un módulo de núcleo o de FUSE, que pueda ser tranparentemente empleado desde terceros programas, cae aquí</td><td>La interfaz usuario muestra trabajo, pero requiere consultar la documentación para comprender el uso</td><td>La interfaz usuario es suficiente para presentar y manipular los datos, pero su uso requiere comprender el código fuente</td><td>El programa es imposible de utilizar exitosamente sin conocer la implementación detalladamente</td><td></td></tr>
<tr><td><b>Documentación</b></td><td></td><td></td><td></td><td></td><td>20%</td></tr>
<tr><td><em>Documentación externa</em></td><td>Incluye una sección de presentación con, por lo menos: Nombres de los participantes del equipo, problema que busca resolver, lógica de operación (cómo lo resuelve), y ejemplos de uso o invocación</td><td>Incluye tres de los puntos mencionados</td><td>Incluye dos de los puntos mencionados</td><td>No incluye documentación del proyecto.</td><td></td></tr>
<tr><td><em>Entorno y dependencias</em></td><td>Presenta el entorno para el cual el programa fue escrito, detallando según sea pertinente lenguaje (incluyendo la implementación y versión mayor), principales módulos que deben ser instalados (con sus respectivas versiones), y demás instrucciones pertinentes</td><td></td><td> Indica los principales componentes requeridos para la construcción y ejecución del proyecto, pero omite detalles importantes que dificultan su exitosa ejecución</td><td></td><td></td></tr>
<tr><td><em>Comentarios</em></td><td>El código está comentado donde hace falta, no repite información obvia. Los comentarios ayudan a comprender la lógica, no la implementación</td><td>El código está comentado donde hace falta, pero los comentarios son excesivos: Además de la lógica general, mencionan lo obvio</td><td>Hay algunos comentarios útiles en el programa, pero falta mucho para que ayude a una buena comprensión</td><td>No hay comentarios</td><td></td></tr>
<tr><td><b>Entrega</b></td><td></td><td></td><td></td><td></td><td>20%</td></tr>
<tr><td><em>Historia en Git</em></td><td>El proyecto consta de un mínimo de cinco <em>commits</em>, con información suficientemente descriptiva para comprender el proceso de desarrollo</td><td>Consta de un mínimo de cinco <em>commits</em>, pero no presentan un título/comentario suficiente para entender el proceso de desarrollo</td><td>La entrega consta de un sólo <em>commit</em>, no permite entender el proceso de desarrollo del proyecto</td><td>No entregó usando Git</td><td></td></tr>
<tr><td><em>Directorio de proyecto</em></td><td>El trabajo entregado consta exclusivamente del código fuente y la documentación, en una estructura acorde para su construcción/compilación, evaluación y uso directo; en caso de requerirlo, un <tt>.gitignore</tt> mantiene limpio el directorio al compilar</td><td>Sólo el código fuente y la documentación forman parte de los <em>commits</em>, pero construir / ejecutar el código <em>ensucia</em> al repositorio (¿debería manejar <tt>.gitignore</tt>?)</td><td>El trabajo entregado incluye archivos innecesarios (como archivos objeto ya compilados o subdirectorios generados por el entorno de desarrollo empleado</td><td>No entregó usando Git</td><td></td></tr>
<tr><td><em>Código válido</em></td><td>Al ejecutar las instrucciones documentadas, el código puede ejecutarse exitosamente al primer intento</td><td>Las instrucciones que forman parte de la documentación tienen que adecuarse para poder ejecutar el código, o hay errores menores que corregir para que funcione</td><td>No está documentado cómo ejecutar el código, o hay errores mayores que corregir para poder ejecutarlo</td><td>Resultó imposible probar la ejecución</td><td></td></tr>
<tr><td><b>Legibilidad</b></td><td></td><td></td><td></td><td></td><td>20%</td></tr>
<tr><td><em>Estructura</em></td><td>El código está bien organizado y emplea un estilo de indentación de forma consistente</td><td>El código está mayormente indentado; hay inconsistencias menores</td><td>Falta claridad en los bloques por no emplear indentación o hacerlo de forma absolutamente inconsistente</td><td></td><td></td></tr>
<tr><td><em>Nomenclatura</em></td><td>Los nombres de los símbolos (variables, funciones, métodos, clases) son claros y acorde a su función; los principales elementos están documentados expresamente</td><td>Los nombres de los símbolos mencionados son claros y acorde a su función, aunque no estén documentados</td><td>Los nombres de los símbolos no son claros, pero su uso y significado forma parte de la documentación</td><td>Cuesta trabajo seguir la lógica; los símbolos empleados no tienen nombres significativos, y su función no está documentada</td><td></td></tr>
</table>
