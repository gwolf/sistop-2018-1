# Proyecto 2
## Monitor

### Autores
* Ferrusca Ortiz Jorge Luis
* Hernández Gonzalez Ricardo Omar

### Lenguaje

Python 3

### ¿Cómo ejecutarlo?

Escribir el siguiente comando en terminal

```
python3 Monitor.py
```

### Dependencias

Se necesitan instalar los siguientes modulos:

* *psutil* - Descargar la versión 5.4.1, de [Python psutil](https://pypi.python.org/pypi?:action=display&name=psutil#downloads), descomprimirlo, entrar a la carpeta descomprimida y escribir el siguiente comando en terminal

```
sudo python3 setup.py install
```

Én caso de *error*, puede que sea necesario escribir previamente este comando

```
sudo apt-get install python3-dev
```

* *tkinter* - Ejecutar en terminal

```
sudo apt-get install python3-tk
```

### Descripción:

El Monitor muestra datos como: Memoria, porcentaje de CPU, el Procesador, y lo más importante, los procesos que se estén ejecutando. El programa en general consiste en sacar información del directorio /proc/, estos datos fueron consultados en la [siguiente liga](http://web.mit.edu/rhel-doc/4/RH-DOCS/rhel-rg-es-4/ch-proc.html), donde mencionan a detalle qué contiene cada archivo de ese directorio. Para esto se hizo implementación del módulo "subprocess", el cual nos ayuda a interacturar con comandos de linux. Casi para finalizar el proyecto nos dimos cuenta que con el modulo "psutil" hubiera sido más fácil adquirir esta información.

Nota: no encontramos como "refrescar" la interfaz, por lo que optamos a poner un botón para actualizar la interfaz, el cual obliga a que cierre por completo la ventana y al momento de volverse a llamar la función es cuando se vuelven a mostrar los datos.
El programa tiene un problema, no muestra todos los procesos que están corriendo, se lo debemos. :(

#### Entorno
Fue programado en sistemas basados en Debian.

#### Interfaz
Se hizo uso del modulo *tkinter* para la interfaz gráfica.
