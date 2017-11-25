# Proyecto 2: Monitor

###Autor:

Hernández González Ricardo Omar
Ferrusca Ortiz Jorge Luis

###Lenguaje:

Python 3

###¿Cómo ejecutarlo?

python3 Monitor.py

###Dependencias:

Se necesita instalar los siguientes paquetes:
* psutil:
Entrar a la siguiente liga https://pypi.python.org/pypi?:action=display&name=psutil#downloads, descargar la versión 5.4.1, descomprimirlo, entrar a la carpeta y escribir el siguiente comando en terminal:

sudo python3 setup.py install

###Descripción:

El Monitor muestra datos como: Memoria, porcentaje de CPU, tu SO, y lo mas importante, los procesos que se estén ejecutando.
El programa en general consiste en sacar información del directorio /proc/, estos datos fueron cosultados en la siguiente liga http://web.mit.edu/rhel-doc/4/RH-DOCS/rhel-rg-es-4/ch-proc.html, donde te mencionan a detalle qué contiene cada archivo de ese directorio. Para esto se hizo implementación del módulo "subprocess", el cual nos ayuda a interacturar con comandos de linux. Casi para finalizar el proyecto nos dimos cuenta que con el modulo psutil hubiera sido más fácil adquirir esta información.

###Entorno
Fue programado en un sistema basado en Debian.

###Interfaz
Se hizo uso del modulo tkinter para la interfaz gráfica.
