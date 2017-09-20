#_*_ encoding: utf-8 _*_
import os
class Existencia:
	rut = raw_input("Ingresa tu ruta: ")
	if rut == NULL:
		rut='.'
class Recorrer(Existenci):
	def  run(self):
		if os.path.isfile(rut)== True:
			for dirName, subdirList, fileList in os.walk(rut):
				print('Directorio: %s' % dirName)
    			for fname in fileList:
        			print('\t%s' % fname)
		else:
			print("No existe el directorio")




direc=Recorrer()
direc.run()



