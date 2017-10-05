import os
import stat
import errno

import fuse
from fuse import fuse
if not hasattr(fuse, '__version__'):
    raise RuntimeError  
        
fuse.fuse_python_api = (0, 2)

import logging
LOG_FILENAME = 'dictfs.log'
logging.basicConfig(filename=LOG_FILENAME,level=logging.DEBUG)

# Only make one of this whe getstat() is called. Real FS has one per entry (file
# or directory).
#
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

    # Return list of path elements as string
    def __join_path(self, path):
        joined_path = '/'
        for element in path:
            joined_path += (element + '/')
        return joined_path[:-1]

    # Return dict of a given path
    def __get_dir(self, path):
        level = self.root
        path = self.__path_list(path)
        for entry in path:
            if level.has_key(entry):
                if type(level[entry]) is dict:
                    level = level[entry]
                else:
                    # Walk over files?
                    return {}
            else:
                # Walk over non-existent dirs?
                return {}
        return level

    # Return dict of a given path plus last name of path
    def __navigate(self, path):
        # Path analysis
        path = self.__path_list(path)
        entry = path.pop()
        # Get level
        level = self.__get_dir(self.__join_path(path))
        return level, entry

    ### FILESYSTEM FUNCTIONS ###

    def getattr(self, path):
        st = MyStat()
        logging.debug('*** getattr(%s)', path)

        # Ask for root dir
        if path == '/':
            #return self.root.stats
            st.st_mode = stat.S_IFDIR 
            st.st_nlink = 2
            return st

        level, entry = self.__navigate(path)

        if level.has_key(entry):
            # Entry found
            # is a directory?
            if type(level[entry]) is dict:
                st.st_mode = stat.S_IFDIR 
                st.st_nlink = 2
                logging.debug('*** getattr_dir_found: %s', entry)
                return st
            # is a file?
            if type(level[entry]) is str:
                st.st_mode = stat.S_IFREG #| 0666 # rw- rw- rw-
                st.st_nlink = 1
                st.st_size = len(level[entry])
                logging.debug('*** getattr_file_found: %s', entry)
                return st

        # File not found
        logging.debug('*** getattr_entry_not_found')
        return -errno.ENOENT

   
