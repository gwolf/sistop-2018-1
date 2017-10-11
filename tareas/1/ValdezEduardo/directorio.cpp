#include <iostream>
#include <dirent.h>//Archivo de cabecera para realizar operaciones en directorios.

using namespace std;

void list_dir(string dir){
	DIR * directorio;
	struct dirent * archivos;//Devuelve información sobre las entradas de directorio actual.
  	string archivos_directorio;
  	if (directorio = opendir(dir.c_str())){ //Se abre el directorio actualmente referido.
		while (archivos = readdir(directorio)){//Se muestra el contenido del directorio actual.
    		archivos_directorio = archivos->d_name;
    		if (archivos_directorio != "." && archivos_directorio != "..") cout << archivos_directorio << endl;   
   		}            
  	}
  			closedir(directorio);//Termina operación sobre directorio. Se cierra el directorio actual.
}

void init(){  
	cout << "Ingrese la ruta a visualizar: ";
  	string dir;
  	getline(cin, dir);//Recibe parámetros del directorio.
  	list_dir(dir);//Lista el directorio actual.   
  	init();
}

int main(int argc, char *argv[]){
	init(); 
 	system("GETCH");//Espera una pulsación del teclado del usuario para realizar la próxima operación.
 	return 0;
}
