	
import os
import stat
import errno

import fuse
from fuse import Fuse
if not hasattr(fuse, '__version__'):
    raise RuntimeError, \
        "python-fuse doesn't know of fuse.__version__, probably it's too old."
fuse.fuse_python_api = (0, 2)

import logging
LOG_FILENAME = 'dictfs.log'
logging.basicConfig(filename=LOG_FILENAME,level=logging.DEBUG)


	# Manda a llamar getstat(), y tiene una entrada de archivo 
class MyStat(fuse.Stat):
    def __init__(self):
        self.st_mode = 0
        self.st_ino = 0
        self.st_dev = 0
        self.st_nlink = 0
        self.st_uid = 0
        self.st_gid = 0
        self.st_size = 0
        self.st_atime = 0
        self.st_mtime = 0
        self.st_ctime = 0
	#Se almacena en memoria el archivo que entro
# My FS, only stored in memory :P
#
class DictFS(Fuse):
    """
    """
    def __init__(self, *args, **kw):
        Fuse.__init__(self, *args, **kw)

        # Root dir
        self.root = {}

    # Return string path as list of path elements
    def __path_list(self, path):
        raw_path = path.split('/')
        path = []
        for entry in raw_path:
            if entry != '':
                path.append(entry)
        return path


    # regresa la lista de la ruta como cadena
    def __join_path(self, path):
        joined_path = '/'
        for element in path:
            joined_path += (element + '/')
        return joined_path[:-1]

    # retorno de camino camno dado
    def __get_dir(self, path):
        level = self.root
        path = self.__path_list(path)
        for entry in path:
            if level.has_key(entry):
                if type(level[entry]) is dict:
                    level = level[entry]
                else:
                    return {}
            else:
                return {}
        return level

    #Retorno de un directorio por apellido
    def __navigate(self, path):
        # Analiza la ruta
        path = self.__path_list(path)
        entry = path.pop()
        # observa en que nivel se enceuntra
        level = self.__get_dir(self.__join_path(path))
        return level, entry

    ### Funciones de archivos

    def getattr(self, path):
        st = MyStat()
        logging.debug('*** getattr(%s)', path)

        # en donde esta la raiz del directorio
        if path == '/':
            
            st.st_mode = stat.S_IFDIR | 0755
            st.st_nlink = 2
            return st

        level, entry = self.__navigate(path)

        if level.has_key(entry):
            # Encuentra la entrada, con permisos
            if type(level[entry]) is dict:
                st.st_mode = stat.S_IFDIR | 0755 # rwx r-x r-x
                st.st_nlink = 2
                logging.debug('*** getattr_dir_found: %s', entry)
                return st
            # Es un Directorio
            if type(level[entry]) is str:
                st.st_mode = stat.S_IFREG | 0666 # rw- rw- rw-
                st.st_nlink = 1
                st.st_size = len(level[entry])
                logging.debug('*** getattr_file_found: %s', entry)
                return st

        # Directorio no encontrado
        logging.debug('*** getattr_entry_not_found')
        return -errno.ENOENT

    def readdir(self, path, offset):
        logging.debug('*** readdir(%s, %d)', path, offset)       

        file_entries = ['.','..']

        # Obtener lista de archivos
        level = self.__get_dir(path)

        # Entradas del directorio
        if len(level.keys()) > 0:
            file_entries += level.keys()

        file_entries = file_entries[offset:]
        for filename in file_entries:
            yield fuse.Direntry(filename)

    def mkdir ( self, path, mode ):
        logging.debug('*** mkdir(%s, %d)', path, mode)

        level, entry = self.__navigate(path)

        # Crear nuevo directorio
        level[entry] = {}

    def mknod ( self, path, mode, dev ):
        logging.debug('*** mknod(%s, %d, %d)', path, mode, dev)

        level, filename = self.__navigate(path)

        # Creacion de archivo vacio
        level[filename] = ''

    #Funcion que mantiene abierto la lista de los directorios
    ## Se comprueban permisos 
    def open ( self, path, flags ):
        logging.debug('*** open(%s, %d)', path, flags)

        level, filename = self.__navigate(path)

        #Verifica existencia del Directorio
        if not level.has_key(filename):
            return -errno.ENOENT


    # Funcion llamado close, este metodo hace la operacion inversa a open
    def release ( self, path, flags ):
        logging.debug('*** release(%s, %d)', path, flags)

        level, filename = self.__navigate(path)

       
        if not level.has_key(filename):
            return -errno.ENOENT

    def read ( self, path, length, offset ):
        logging.debug('*** read(%s, %d, %d)', path, length, offset)

        level, filename = self.__navigate(path)

       
        if not level.has_key(filename):
            return -errno.ENOENT

        # Compureban los rangos
        file_size = len(level[filename])
        if offset < file_size:
   # 
            if offset + length > file_size:
                length = file_size - offset
            buf = level[filename][offset:offset + length]
        else:
            # Rango invalido
            buf = ''
        return buf

    def rmdir ( self, path ):
        logging.debug('*** rmdir(%s)', path)

        level, entry = self.__navigate(path)

       
        if not level.has_key(entry):
            return -errno.ENOENT

        # Delete entry
        del(level[entry])

    def truncate ( self, path, size ):
        logging.debug('*** truncate(%s, %d)', path, size)

        level, filename = self.__navigate(path)

        # File exists?
        if not level.has_key(filename):
            return -errno.ENOENT

        if len(level[filename]) > size:
            # Truncate file to specified size
            level[filename] = level[filename][:size]
        else:
            # Add more bytes
            level[filename] += ' ' * (size - len(level[filename]))
        
    def unlink ( self, path ):
        logging.debug('*** unlink(%s)', path)

        level, entry = self.__navigate(path)

        
        if not level.has_key(entry):
            return -errno.ENOENT


        del(level[entry])

    def write ( self, path, buf, offset ):
        logging.debug('*** write(%s, %s, %d)', path, str(buf), offset)

        level, filename = self.__navigate(path)

        # Escribir los datos del archivo
        if offset > len(level[filename]):
            offset = (offset % len(level[filename]))
        level[filename] = level[filename][:offset] + str(buf)
        
        # Regresan bytes escritos
        return len(buf)

    def rename ( self, oldPath, newPath ):
        logging.debug('*** rename(%s, %s)', oldPath, newPath)

        oldLevel, oldFilename = self.__navigate(oldPath)
        # si no esite filename, no se usa navigate
        newPath = self.__path_list(newPath)
        newFilename = newPath.pop()
        newLevel = self.__get_dir(self.__join_path(newPath))

        # hacer nuevo link
        newLevel[newFilename] = oldLevel[oldFilename]

        # quitar viejos
        self.unlink(oldPath)

def main():
    usage = """
Userspace filesystem example
""" + Fuse.fusage
    
    fs = DictFS(version = '%prog' + fuse.__version__,
                usage = usage,
                dash_s_do='setsingle')
    fs.parse(errex = 1)
    fs.main()

if __name__ == '__main__':
    main()

