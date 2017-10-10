*PROYECTO 1: Mini Sistema de Archivos.*

*Integrantes:*
Ferrusca Ortíz Jorge Luis
Hernández González Ricardo Omar

*Lenguaje de programación:* Java

*¿Qué se necesita para ejecutar el programa?*

1. Tener instalado Java:
	Hay dos formas de instalarlo:
		
		*Línea de comandos*

		Poner en línea de comandos lo siguiente:
		sudo add-apt-repository ppa:webupd8team/java
		sudo apt-get update
		sudo apt-get install oracle-java8-installer
		
		*Descargándolo desde su página e instalarlo*

		Meterse a la página: http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html
		y elegir qué sistema operativo.

2. Para comprobar que tienes java teclea en línea de comandos lo siguiente:
	
	javac -version

Este comando te dirá que versión de java tienes, en caso de no tenerlo te lo dirá.

3. Primero tienes que compilar el programa:
	Para compilar un .java tienes que poner en la terminal "javac" y el nombre de tu programa, por ejemplo:

	javac programa.java

4. Ejecutar el programa:
	Para ejecutar un programa solo basta con poner en linea de comandos, "java" seguido del programa SIN el .java, por ejemplo:

	java programa

*Acerca del programa*

La clase principal que se debe compilar y ejecutar es "ManejadorArchivos.java", la clase "ModeloTablaArchivo.java" solo se utiliza para la clase principal.

El programa cuenta con una interfaz gráfica en la cual, del lado izquierdo te muestra las carpetas con los que cuenta tu computadora, al momento de seleccionar una carpeta te muestra en la parte derecha, los archivos y carpetas que se encuentren en esta. Para abrir o editar solo debes seleccionar el archivo y darle al botón de la opción que desees. También cuenta con un botón de imprimir.
En la parte donde te muestra lo que contiene tu directorio, viene el icono del archivo, seguido del nombre, la dirección donde se encuentra y otros datos como el tipo de permisos que tiene cada archivo. En la parte inferior también viene el tipo de archivo. 




