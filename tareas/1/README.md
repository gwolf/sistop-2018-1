# Recorriendo un directorio

Presenté en clase un ejemplo sencillo en C de cómo mostrar los
archivos de un directorio. Lo reproduzco a continuación:

    #include <stdio.h>
	#include <dirent.h>
	#include <sys/types.h>
	
	int main(int argc, char *argv[]) {
        struct dirent *archivo;
		DIR *dir;
		if (argc != 2) {
			printf("Indique el directorio a mostrar\n");
			return 1;
		}
		dir = opendir(argv[1]);
		while ((archivo = readdir(dir)) != 0) {
			printf("%s\t", archivo->d_name);
		}
		printf("\n");
		closedir(dir);
	}

La tarea consiste en extender o reemplazar este programa para que
_recorra_ los subdirectorios a un nivel de profundidad. Esto es, para
cada `dirent` que vaya encontrando, que determine si es un archivo o
un directorio, y en caso de ser un directorio, que presente sus
contenidos.

Como indiqué en clase, _prefiero_ que desarrollen este programa en C,
porque sé que maneja las estructuras `dirstream` (`DIR`) y `dirent`,
que manejan las operaciones que abordamos para esto. Si lo quieren
desarrollar en otro lenguaje, se vale, pero verfiquen que presenta un
modelo de abstracción similar.

Entregar lo descrito hasta aquí les da un 8 de calificación. Para
obtener el 10, sigan leyendo.

## Precisiones de la entrega

Esta tarea puede ser entregada _de forma individual_ o _en equipos de
dos personas_.

### ¿Qué y dónde?

Dentro del directorio que generen (siguiendo, como siempre, el
estándar de nombres presentado en la sección 4 de la
[práctica 1](../../practicas/1/README.md)) debe haber dos archivos: El
programa en C, y un archivo de documentación; éste puede ser tan
sencillo como gusten; puede ser en formato de texto plano, un
documento de procesador de texto o un PDF. Debe contener:

- Entorno/ambiente en que lo desarrollaron (¿Unix? ¿Windows? ¿Qué
  compilador utilizaron? ¿Qué bibliotecas emplearon _fuera_ de la
  biblioteca estándar de C?)
- Instrucciones de compilación y uso para el programa: ¿Cómo debo
  invocarlo? ¿Qué argumentos recibe?
- Salida esperada: ¿Cómo se debe ver la salida?

### Para aumentar la calificación

Para obtener el 10... Demuestren un poco de creatividad :-)

Si hacen que el programa formatee la salida de forma más amigable, que
presente de qué tipo son los archivos con que se va encontrando, que
se le pueda especificar por parámetros el nivel de profundidad a que
va a entrar, que entregue la salida ordenada... No quiero robarles sus
ideas. ¡Sean creativos! Hagan de este programa _algo más bonito_ y/o
_algo más útil_, y tendrán el 10.

## Calificaciones

Pueden consultar
[las calificaciones que han obtenido y comentarios a sus trabajos](./calificaciones.org).
