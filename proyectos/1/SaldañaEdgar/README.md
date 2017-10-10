# Microsistema de archivos Sistop #

Este es el primer proyecto de la materia *Sistemas Operativos*. Fue desarrollado por Edgar Saldaña Nieves durante el semestre *2018-1*.

## ¿Qué y para qué? ##
Sistop es un microsistema de archivos, es decir, el problema que busca resolver es la organización de archivos a través de estructuras. Esto incluye acciones como listar el contenido de un directorio, crear, editar y eliminar archivos, así como un manejo básico de permisos.

## ¿Cómo lo hace? ##
La lógica de implementación es sencilla: el sistema utiliza como volumen de montaje un directorio de tu sistema operativo. Dentro de él realiza todas las operaciones y guarda las estructuras necesarias para un adecuado funcionamiento, como el superbloque, el directorio raíz y el espacio de datos. A su vez, el directorio raíz almacena en dos listas dinámicas los inodos y los nombres de archivos correspondientes, respectivamente.

## Entorno y especificaciones técnicas ##
Sistop fue desarrollado en lenguaje Java, utilizando el JDK versión 1.8.0_131 en entorno Linux. Al ser Java un lenguaje multiplataforma, Sistop podrá ser ejecutado en cualquier sistema operativo que admita el JDK, el cual puede ser descargado desde su [página oficial](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). Para compilarlo bastará con escribir en la terminal:

`$ javac *.java`

Después solo deberás ejecutarlo dentro del directorio que se usará como volumen de la siguiente manera:

`$ java Sistema`

## Uso de comandos ##
Con el comando `help` podrás desplegar una lista de los posibles comandos que podrás invocar, estos son:

### ls ###
Lista el contenido del directorio raíz, se invoca:

`R>ls`

Aparecerá una tabla como la siguiente:

    dir1            directorio  Mon Oct 09 21:27:02 CDT 2017     0 bytes  RW  6
    dir2            directorio  Mon Oct 09 21:27:07 CDT 2017     0 bytes  RW  17
    hola.txt        archivo     Mon Oct 09 21:27:15 CDT 2017     0 bytes  RW  26
    tareas          directorio  Mon Oct 09 21:27:28 CDT 2017     0 bytes  RW  39
    programa.c      archivo     Mon Oct 09 21:28:04 CDT 2017     0 bytes  RW  10
    documento.txt   archivo     Mon Oct 09 21:28:42 CDT 2017     0 bytes  RW  38

Donde en la primera columna se muestra el **nombre de archivo**, en la segunda el **tipo de archivo**, en la tercera la **fecha de creación**, en la cuarta el **tamaño del archivo**, en la quinta los **permisos** con los que se cuentan, y en la última columna el **inodo** que le corresponde.

*Nota: A pesar de que el programa tiene estructuras pensadas para que el comando ls pueda aplicarse no solo a la raíz sino a cualquier subdirectorio, no se alcanzó a implementar*

### mkd ###
Crea un nuevo directorio en la raíz y se le debe pasar como argumento el nombre, se invoca, por ejemplo:

`R>mkd juegos`

Si esta acción no devuelve ningún texto quiere decir que se creó con éxito el nuevo directorio llamado *juegos*.

*Nota: No se admiten nombres de directorios que contengan espacios en blanco*

### mkf ###
Crea un nuevo archivo en la raíz y se le debe pasar como argumento el nombre. Por ejemplo:

`R>mkf programa.c`

Si esta acción no devuelve ningún texto quiere decir que se creó con éxito el nuevo archivo llamado *programa.c*.

*Nota: No se admiten nombres de archivos que contengan espacios en blanco*

### rem ###
Elimina el archivo especificado, ya sea directorio o regular. Por ejemplo:

`R>rem proyectos`

Si esta acción no devuelve ningún texto, se eliminó correctamente el archivo *proyectos*

### chmod ###
Cambia los permisos del archivo especificado. Por defecto todos los archivos tienen habilitados los permisos de lectura y escritura. Supongamos que deseo desactivar ambos permisos al archivo *programa.c*, entonces invoco:

`R>chmod programa.c -w-r`

Con esto se desactivarán ambos permisos. Si deseo volver a activar solamente la lectura, escribo:

`R>chmod programa.c +r`

Existen 4 opciones posibles para modificar los permisos:

* `+w`    habilitala escritura
* `-w`    deshabilita la escritura
* `+r`    habilita la lectura
* `-r`    deshabilita la lectura

Es importante observar que en el argumento de los permisos no se deben introducir espacios.

### look ###
Muestra en pantalla el contenido del archivo especificado. Se invoca, por ejemplo:

`R>look programa.c`

Esto dará como salida:

    #include <stdio.h>
    
    int main()
    {
        printf("Hola mundo");
        return 0;
    }

### add ###
Agrega texto al archivo especificado, únicamente se pasa como argumento el nombre del archivo a modificar. Si, por ejemplo, deseas agregar texto a *hola.txt* debes invocar:

`R>add hola.txt`

Ahora aparecerá el mensaje:

    Escribe lo que agregarás al archivo:


En el espacio indicado escribe lo que agregarás, al pulsar la tecla enter se guardará en él.

### enter ###
Ingresa al directorio especificado. Si deseas entrar, por ejemplo, al directorio *tareas*, deberías escribir:

`R>enter tareas`

Al hacerlo, observarás un cambio en la ruta donde te encuentras:

`R>tareas> `

*Nota: A pesar de que es posible entrar a un directorio, no se han implementado las funciones necesarias para llevar a cabo operaciones dentro de él.*

### back ###
Regresa al directorio anterior y no recibe argumentos, basta con escribir:

`R>tareas>back `

Para así de nuevo encontrarnos en la raíz del sistema:

`R> `

### super ###
Muestra la información almacenada en el superbloque, se invoca:

`R>super`

### exit ###
Abandona el programa.

*Importante: es necesario usar el comando **exit** para finalizar la ejecución, en caso de no hacerlo se perderán datos importantes*
