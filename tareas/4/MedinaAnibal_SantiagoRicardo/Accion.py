#importaremos la libreria random 
import random
class Accion:
	#crearemos nuestro metodo que dara las direcciones de los autos
	def Actions(self):
		direccion=["sigue de frente","gira a la derecha","gira a la izquierda"]
		print("coche\t"+random.choice(direccion)+"\n")
		return