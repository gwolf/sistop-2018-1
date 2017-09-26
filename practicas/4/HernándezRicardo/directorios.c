#include <stdio.h>
#include <dirent.h>
#include <stdint.h>

int32_t main(int32_t argc, char *argv[]){
	DIR * restrict carpeta;
	struct dirent * restrict contenido;
	int32_t i;
	long desplazamiento;

	carpeta = opendir("/"); //Abre flujo a la carpeta

	//Se va a listar el contenido de la carpeta
	for (i=1; (contenido= readdir(carpeta)); i++) {
		printf("%d. %s\n",i,contenido->d_name);
		if (i == 5)
			desplazamiento = telldir(carpeta); //Guardamos la posicion
	}

	rewinddir(carpeta); //Regresamos al inicio
	seekdir(carpeta,desplazamiento); //Desplazamos el apuntador

	printf("\nDespues de desplazar a la quinta posicion\n\n");
	for(i=1; (contenido= readdir(carpeta)) != NULL ;i++){
		printf("%d. %s\n",i,contenido->d_name);
	}

	closedir(carpeta); //Cierra flujo


	return 0;
}
