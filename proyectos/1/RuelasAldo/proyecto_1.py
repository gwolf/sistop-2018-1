#!/usr/bin/python
import fs       #libreria pyfilesystem con la que se hace las operaciones
import sys      #librerias para elegir las opciones
import getopt

home_fs = fs.open_fs('~/')  #directorio por defecto

HELP = (
    'uso python <comando> <nombre del archivo>'
    'options:\n'
    '         -l --listar   lista el directorio actual\n'
    '         -c --crear   crea un archivo\n'
    '         -s --suprime suprime el archivo  \n'
    '         -m --muestra muestra el contenido del archivo\n'
    '         -e --escribe en el archivo    help\n'
    '         -a --agrega  agrega al final del archivo \n'
    '         -h --help    help\n'
)


def get_command_line_options():
    """Funcion para elegir las opciones del programa"""

    argv = sys.argv[1:]
    try:
        opts, args = getopt.getopt(argv, 'lhcsmea:',
                                   ['listar', 'help', 'crear', 'suprime', 'muestra', 'escribe', 'agrega'])
    except getopt.GetoptError:
        print(HELP)
        sys.exit(2)
    for opt, arg in opts:
        if opt in ('-h', '--help'):
            print(HELP)
            sys.exit()
        elif opt in ('-l', '--listar'):
            listardir()
        elif opt in ('-c', "--crear"):
            if len(argv) > 2:
                print('demasiados argumentos')
            crea_archivo(argv[1])
        elif opt in ('-s', '--suprime'):
            if len(argv) > 2:
                suprime_archivo(argv[1])
                print('se borro el archivo', argv[1])
            else:
                print('el archivo', argv[1], 'no existe')
        elif opt in ('-m', '--muestra'):
            if len(argv) > 2:
                print('demasiados argumentos')
            else:
                mostrar_contenido(argv[1])

        elif opt in ('-e', '--escribe'):
            if len(argv) > 3:
                print('demasiados argumentos')
            else:
                escribe_archivo(argv[1], argv[2])

        elif opt in ('-a', '--agrega'):
            if len(argv) > 3:
                print('demasiados argumentos')
            else:
                agrega_a_archivo(argv[1], argv[2])


def listardir():
    home_fs.tree(max_levels=0)


def crea_archivo(nombre_archivo):
    if home_fs.exists(nombre_archivo):
        print('El archivo ya existe')
    else:
        home_fs.create(nombre_archivo)


def suprime_archivo(nombre_archivo):
    if not home_fs.exists(nombre_archivo):
        print('El archivo que  estas tratando de eliminar no existe')
    else:
        home_fs.remove(nombre_archivo)


def mostrar_contenido(nombre_archivo):
    if not home_fs.exists(nombre_archivo):
        print('El archivo no existe')
    else:
        print(home_fs.gettext(nombre_archivo))


def escribe_archivo(nombre_archivo, agregar_text):
    if not home_fs.exists(nombre_archivo):
        print('El archivo no existe')
    else:
        home_fs.settext(nombre_archivo, agregar_text)


def agrega_a_archivo(nombre_archivo, agregar_txt):
    if not home_fs.exists(nombre_archivo):
        print('El archivo no existe')
    else:
        home_fs.appendtext(nombre_archivo, agregar_txt)


def main():
    get_command_line_options()


if __name__ == "__main__":
    main()
