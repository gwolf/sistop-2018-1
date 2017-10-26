# se define la pila que servira como almacenamiento
class Pila:
	def __init__(self):
		self.items=[]
	def push(self, x):
		self.items.append(x)
	def pop(self):
		try:
			return self.items.pop()
		except IndexError:
			raise ValueError("no existen elementos");
	def top(self):
		return self.items[0]
	def tam(self):
		return len(self.items)
	def vacia(self):
		return self.items ==[]
