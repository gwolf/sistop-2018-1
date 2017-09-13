//realiza el recorrido de un directorio

#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

void dirContenido(char *directorio);
	
int main(int argc, char *argv[]) {
									//define un apuntador a Directotio
	if (argc != 2) {				//verifica que el usuario halla indicado el nombre del directorio antes de ejecutar el programa
		printf("Indique el directorio a mostrar\n");
		return 1;
	}
	dirContenido(argv[1]);			//se listara el directorio indicado en consola
	printf("\n");
}

void dirContenido(char *directorio){
	DIR *dir;						//apuntador a directorio
	struct dirent *archivo;			//define una apuntador a estructura dirent
	struct stat info;				//define una variable tipo stat para obtener informacion del fichero/directorio
	dir = opendir(directorio);			//abre el directorio indicado.
	while ((archivo = readdir(dir)) != NULL	) {
		if((strcmp(archivo->d_name,".") != 0)&&(strcmp(archivo->d_name,"..") != 0)!= 0){	//verifica que el archivo no sea el directorio actual o el directorio padre
			stat(archivo->d_name,&info);	//si es diferente del dir actual o padre, llena la estructura info con la informacion del nuevo directorio
			if (S_ISDIR(info.st_mode))				//verifica si el archivo es un fichero o directorio
			{
				printf("i've found a New directory: showing contents\n");
				dirContenido(archivo->d_name);	//lista a lo la estructura dirent tenga en su campo d_name
			}else{
				printf("%s\t\n", archivo->d_name);	//muestra el contenido del directorio	
			}//fin else
		}//FIN IF
	}//fin while
	closedir(dir);
}//fin dirContenido