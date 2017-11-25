#include <stdio.h>
#include <math.h>

int idProceso, pagina, i, j;
int memoria[16], areaLibre[5][8];
int apMemoria, dirAreaLibre, dirMemoria, movAreaLibre, posMemoria;

//Funcion para determinar el tamanio del bloque
int tamAreaLibre(int posicion){
	int size = pow(2, posicion);
	return size;
}

//Muestra la situacion actual de la memoria y del vector de areas libres
void mostrarMemArea(){
	printf("\n\t**Situacion memoria Real**\n");
	for(i = 0; i < 16; i++)
		printf("\n\t%d\t|\t%d\t|",i, memoria[i]);
	printf("\n\n\t**Situacion vector de Areas Libres**\n");
	for(i = 0; i < 5; i++){
		printf("\n\t%d\t|", tamAreaLibre(i));
		for(j = 0; j < 8; j++)
			printf("%d\t", areaLibre[i][j]);
		printf("|");
	}
	printf("\n\n");
}

//Inicializa el vector de areas libres
// con -1 esta vacia
void iniAreaLibre(){
	int i, j;
	for (i = 0; i < 5; ++i){		
		for (j = 0; j < 8; ++j){
			areaLibre[i][j]=-1;
		}
	}
}

//Funcion que actualiza el contenido de la memoria real
void actMemReal(){
	int contenido = 0, finRevision = 0;

	dirMemoria = -1;
	iniAreaLibre();

	for(i = 0; i < 16; i++){
		//Cuenta el numero de marcos de pagina consecutivos libres
		if (memoria[i] == -1){
			if (contenido == 0)
				dirMemoria = i;
			contenido++;
			finRevision = 0;
		}
		else{
			finRevision = 1;
		}

		if (i == 15 && memoria[15] == -1)
			finRevision = 1;

		//Ubica y asigna el bloque de paginas en memoria real
		if (finRevision == 1 && dirMemoria != -1){
			while(contenido > 0){
				posMemoria = 0;
				apMemoria = -1;
				if(contenido == 16)
					apMemoria = 4;
				else if ((contenido < 16) && (contenido >= 8))
						apMemoria = 3;
					else if ((contenido < 8) && (contenido >= 4))
							apMemoria = 2;
						else if ((contenido < 4) && (contenido >= 2))
								apMemoria = 1;
							else if (contenido == 1)
									apMemoria = 0;
								else
									printf("\nNo se pudo asignar\n");
				//Actualiza la direccion de memoria real
				while(areaLibre[apMemoria][posMemoria] != -1){
					posMemoria++;
				}
				areaLibre[apMemoria][posMemoria] = dirMemoria;
				dirMemoria += tamAreaLibre(apMemoria);
				contenido -= tamAreaLibre(apMemoria);
			}
			finRevision = 0;
			dirMemoria = -1;
		}
	}
}

//Fucion que asigna las páginas de un proceso a memoria real
void asigMemoria(int idProceso, int pagina){
	int totalPag = pagina;

	//Se identifica el tamanio de bloque
	while(pagina > 0){
		if (totalPag == 16)
			apMemoria = 4;
		else if ((totalPag < 16) && (totalPag >= 8))
				apMemoria = 3;
			else if ((totalPag < 8) && (totalPag >= 4))
					apMemoria = 2;
				else if ((totalPag < 4) && (totalPag >= 2))
						apMemoria = 1;
					else if (totalPag == 1)
							apMemoria = 0;
						else
							printf("\nNo se pudo asignar\n");
		if(areaLibre[apMemoria][0] != -1){
			if(pagina - tamAreaLibre(apMemoria) >= 0){
				dirAreaLibre = areaLibre[apMemoria][0];
				movAreaLibre = tamAreaLibre(apMemoria);
				pagina -= tamAreaLibre(apMemoria);
			}
			else{
				dirAreaLibre = areaLibre[apMemoria][0];
				movAreaLibre = pagina;
				pagina = 0;
			}
			for (i = 0; i < movAreaLibre; i++)
				memoria[dirAreaLibre + i] = idProceso;
			actMemReal();
		}
		if (totalPag == 16)
			totalPag = 0;
		totalPag++;
	}
	actMemReal();
}

//Funcion que libera marcos de pagina
void libMemoria(int idProceso){
	for(i = 0; i < 16; i++){
		if(memoria[i] == idProceso)
			memoria[i] = -1;
	}
	actMemReal();
}

//Funcion qie inicializa el contenido de memoria real.
void iniMem(){
	int i, j;
	for (i = 0; i < 16; ++i)
		memoria[i] = -1; 
}


int main(int argc, char *argv[]){
	if (argc != 2){
		printf("\nFavor de ingresar el nombre de un archivo.\n");
		return 0;
	}

	printf("***** Administracion de Memoria *****\n");
	printf("\nEl nombre del archivo es: %s\n", argv[1]);

	FILE *archivo;
	archivo = fopen(argv[1], "r");

	if (archivo == NULL)
		printf("\nError al abrir el archivo %s\n", argv[1]);

	else{
		printf("\nEl archivo %s se ha abierto con exito\n",argv[1]);

		//Inicializamos la memoria real y el vector de areas libres
		iniAreaLibre();
		iniMem();
		
		//Asignamos el marco de pgina 0 al vector de area Libre en el tamanio 16, por estar todos libres
		areaLibre[4][0] = 0;

		//Conforme se lee el archivo se va asignando o liberando memoria		
		while (fscanf(archivo, "%d %d", &idProceso, &pagina) != EOF){
			if(pagina > 0){
				printf("\n\n\tEl proceso %d requiere %d paginas\n", idProceso, pagina);
				asigMemoria(idProceso, pagina);
			}
			else{
				printf("\n\n\tEl proceso %d termina\n", idProceso);
				libMemoria(idProceso);
			}
			mostrarMemArea();
		}

		fclose(archivo);
		return 0;
	}
}
