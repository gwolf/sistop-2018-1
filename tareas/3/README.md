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
