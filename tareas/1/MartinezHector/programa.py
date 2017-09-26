import os

rootDir = '.'
for dirNAme, subdirList, fileList in os.walk(rootDir):
	print('Este directorio se encuentra en: %s' %dirNAme)
	print ('Lo que integra este subdirectorio es:')	
	for fname in fileList:
		print ('\t%s' %fname)


