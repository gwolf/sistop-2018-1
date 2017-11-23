# La utilización del CPU es incorrecta

Hay muchas herramientas que nos reportan la carga de un programa de
cómputo en términos del porcentaje de uso del procesador. En clase
presentamos cómo lo reportan los sistemas Unix.

El modelo que utilizan estas herramientas... Tristemente es
incompleto.

Les presento un breve artículo/blogazo escrito por Brendan Gregg:
[CPU Utilization is Wrong (La utilización del CPU es incorrecta)](http://www.brendangregg.com/blog/2017-05-09/cpu-utilization-is-wrong.html).

# Instrucciones de entrega

La entrega es individual.

Para esta tarea, en primer término, les pido que lean ese artículo, y
después de eso:

1. Elaboren un mapa mental con las ideas presentadas por el
   artículo. Pueden elaborarlo de forma nativa en digital, o hacerlo
   en papel y fotografiarlo. Incluyan el mapa conceptual como parte de
   su entrega.

2. Verifiquen lo expuesto por el autor. Ejecuten los comandos de
   medición de rendimiento en un sistema Linux; para poder comparar,
   háganlo a baja y alta carga de procesos. Comparen los resultados.

   Si tienen la posibilidad, pueden hacer la comparación entre
   diferentes computadoras.

   Incluyan en su reporte las características de la computadora (sea
   sistema físico o máquina virtual; de preferencia, en un sistema
   físico), particularmente, detallen la carga que estan dando a
   ejecutar, la cantidad de RAM que tiene el sistema, y el tipo de
   procesador (tomen el modelo indicado en `/proc/cpuinfo`)

¿Lograron replicar las conclusiones de Gregg?

# Calificaciones y comentarios

- Héctor Martínez
  - **Calificación: 8.5**
  - ¡Buen desarrollo de mapa mental!
  - ¿Por qué intentaste instalar `linux-tools-common` y no
    `linux-tools` a secas? O, siguiendo lo que recomienda el
    pantallazo que envías, el específico a tu versión —
    `linux-tools-3.19.8-32-generic`?
	
	Explico un poco este punto: `perf` es una herramienta que trabaja
    muy de cerca de la versión específica del núcleo que tienes
    instalado. Por tanto, el paquete tiene que estar separado en tres
    pedazos: `linux-tools-common` tiene las partes que se mantienen
    constantes, y el paquete `linux-tools-3.19.8-32-generic` es
    específico a tu versión del núcleo; el paquete `linux-tools`
    *depende* de ambos (esto es, si instalas `linux-tools`, sus
    *dependencias* serán instaladas automáticamente)
  - ¿Y por qué no intentaste instalar tiptop?

- Ricardo Hernández
  - **Calificación: 8**
  - `perf` te estaba indicando que lo tienes que correr como
    administrador (como root, dentro de `sudo` o como quieras
    interpretarlo)
  - Respecto a `tiptop`, probablemente era únicamente cuestión de
    instalarlo. ¡No puedes esperar tener todos los comandos
    disponibles e instalados! Si tu sistema es derivado de Debian,
    basta con `apt install tiptop`.

## Extemporáneas (×0.8)

- Edgar Saldaña
  - **Calificación: 10 × 0.8 = 8**
  - ¡Muy buena entrega!

- Ricardo Santiago
  - **Calificación: 8 × 0.8 = 6.4**
  - Yo dije que podían entregar fotografía de un papel con el mapa
    mental... Pero cuida la calidad :-( No puedo leer bien lo que
    escribiste
  - De tus capturas de `top` (Linux) y *Administrador de Tareas*
    (Windows), no entraste a lo que sugiere el autor: Ninguna de estas
    herramientas considera las instrucciones por ciclo (IPC) ni el
    rendimiento mejor o peor debido al acceso a memoria.
