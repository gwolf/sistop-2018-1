def main():
	l=["-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"]
	f=5
	e=3
	fr=14
	fre=1
	free=7
	aux=0
	n=""
	aux=0
	while n!="n":
		print("proceso actual\n")
		print(l)
		process = raw_input("Ingrese el proceso:\n")
		cont = input("Ingrese cantidad del proceso\n")
		if (aux<=30):
			if (cont==fre):
				l[0]=process
				fre=fre-1
				aux=aux+1
				print ("mejor ajuste")
			elif (cont <= e):
				i=1 
				j=0
				while j<cont :
					l[i]=process
					i=i+1
					j=j+1
					e=e-1
					aux=aux+1
				if(e<2):
					print("mejor ajuste\n")
				else:
					print ("peor ajuste")
			elif (cont<= f):
				i=4
				j=0
				while j<cont:
					l[i]=process
					i=i+1
					j=j+1
					f=f-1
					aux=aux+1
				if(f<2):
					print("mejor ajuste\n")
				else:
					print ("peor ajuste")
			elif (cont<= free):
				i=9
				j=0
				while j<cont:
					l[i]=process
					i=i+1
					j=j+1
					free=free-1
					aux=aux+1
				if(free<2):
					print("mejor ajuste\n")
				else:
					print ("peor ajuste")
			elif (cont<= fr):
				i=16
				j=0
				while j<cont:
					l[i]=process
					i=i+1
					j=j+1
					fr=fr-1
					aux=aux+1
				if(fr<2):
					print("mejor ajuste\n")
				else:
					print ("peor ajuste")
			elif(cont>fr):
				print("se realizara una compresion")
				i=0
				while i<30:
					if l[i]=="-":
						j=i
						while l[i]=="-":
							if(j<30):
								l[i]=l[j]
								j=j+1
					i=i+1
			else:
				print("Memoria insuficiente")
			print("proceso nuevo:\n")
			print(l)
			ret = raw_input("desea realizar otro proceso? (s/n):\n")
			n=ret
		else:
			print("ERROR!\n")
			print("Memoria insuficenta")

		
		

	print(l)
if __name__ == '__main__':
	main()