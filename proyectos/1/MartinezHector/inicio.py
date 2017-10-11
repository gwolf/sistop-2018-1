# Aqui se crea la interfaz de usuario
from File import FileSystem
class main:
	def Iniciar(self):
		print ("Bienvenido al Sistema de archivos")
		k=""
		while k!="s":
			comando = raw_input("Ingrese el comando$\n")
			if comando == "Crear":
				c= FileSystem()
				c.Crear()
			elif comando == "Eliminar":
				d= FileSystem()
				d.Elimin()
			elif comando == "Buscar":
				b=FileSystem()
				b.Busc()
			elif comando == "Mostrar":
				m=FileSystem()
				m.Mostrar()
			elif comando == "Leer":
				l=FileSystem()
				l.Leer()
			elif comando== "comandos":
				print("--------------------------------------------")
				print("|                comandos                   |")
				print("--------------------------------------------")
				print("|Crear| creas un archivo                    |")
				print("--------------------------------------------")
				print("|Eliminar| eliminas un archivo              |")
				print("--------------------------------------------")
				print("|Buscar| Determina si un archivo existe     |")
				print("--------------------------------------------")
				print("|Mostrar| Muestra los archivos existentes   |")
				print("--------------------------------------------")
				print("|Leer| Te muestra contenido de un archivo   |")
				print("--------------------------------------------")
				print("|comandos| te muestra los comandos          |")
				print("--------------------------------------------")
				print("|FSend| cierra el sistema de archivos       |")
				print("---------------------------------------------")
			elif comando== "FSend":
				print("Seguro que quiere realizar esta operacion?\n s/n")
				elec = raw_input("Ingrese eleccion:\n")
				if elec== "s":
					k="s"

			else:
				print("no esta efinido este comando")

#Aqui es de donde se iniciara el programa
begin = main()
begin.Iniciar()

