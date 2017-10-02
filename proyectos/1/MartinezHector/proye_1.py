import os
import stat
import errno
import fuse

from fuse import Fuse
if not hasattr(fuse, '__version__'):
   # raise RuntimeError, \
        "python-fuse doesn't know of fuse.__version__, probably it's too old."
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


