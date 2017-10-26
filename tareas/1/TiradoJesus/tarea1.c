#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <unistd.h>

void lista_directorio(char *nombre);
void es_directorio(char *nombre);

int main(int argc, char *argv[]) {
	if (argc != 2) {
		printf("Indique el directorio a mostrar\n");
		return 1;
	} else {
		es_directorio(argv[1]);
	}
}

//Funcion que determina si el archivo es un directorio o no
void es_directorio(char *nombre){
	struct stat estru;
    struct dirent *archivo;
    int esDirectorio;
	DIR *dir;
	dir = opendir(nombre);
	while ((archivo = readdir(dir)) != 0) {
		printf("%s\n", archivo->d_name);
		for (int j = 0; j < strlen(archivo->d_name); j++) {
			if (archivo->d_name[j] != '.') {
				esDirectorio = 1;
			} else {
				esDirectorio = 0;
				break;
			}
		}
		if (esDirectorio == 1) {
			lista_directorio(archivo->d_name);
		}else{
			//printf("no es directorio\n" );
		}		
		
	}
	printf("\n");
	closedir(dir);
}



//Función que permite imprimir el subdirectorio
void lista_directorio(char *nombre){
	//Declaramos variables, estructuras
	struct stat estru;
	struct dirent *dt;
	DIR *dire;
	int esDirectorio;

	dire = opendir(nombre);

	printf("-->abriendo el directorio %s\n",nombre);
	//Recorrer directorio
	while((dt=readdir(dire)) != 0){
		//es_directorio(nombre);  
		printf("\t%s\n", dt->d_name);
	}
	printf("-->fin del directorio %s\n",nombre);
	closedir(dire);
}
