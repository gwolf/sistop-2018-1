//Modulo de Pruebas
#include <stdio.h>			//biblioteca ANSI C
#include <stdlib.h>			//biblioteca ANSI C
#include <string.h>
#include "ficheros.h"		//biblioteca para el manejo de ficheros en MySis
#include "other.h"			//biblioteca para el uso de comandos de ayuda

void myShell();							//ejecutara el shell con el que iteractuara el ususario

int main()	
{
	FILE *raiz;
	system("clear");
	printf("**************************************************\n");
	printf("*-------mySys: myShell---------------------------*\n");
	printf("*-------------------------------test ver 1.09----*\n");
	printf("*-help: ayuda---info: acerca de-----quit: salir--*\n");
	printf("**************************************************\n");
	//creamos archivo que simulara ser el directorio raiz de nuestro sistema de archivos
	raiz = fopen("raiz","w");
	fprintf(raiz, "Root del micro sistema\n");
	fclose(raiz);
	myShell();
	return 0;
}


void myShell(){
	char *command;
	char *arg;
	command = malloc(10*sizeof(char));					//asignamos memoria para maximo 10 char en cadena de comandos
	arg = malloc(20*sizeof(char));						//asignamos momoria para maximo 20 chars en cadena de argumentos
	printf("\nmyShell commad ->");						//muestra la linea que pide nombre de comando
	fflush(stdin);
	scanf("%s",command);								//recibe comando
//ejecucion de comandos
//comandos para ficheros
	//comprueba comando nwefil
	if (strcmp(command,"newfil") == 0){				
		printf("args[name] >>");						
		scanf("%s",arg);								//guardamos arg 
		newfil(arg);									//invocamos al comando ya definido y le pasamos un arg
		myShell();										
	}//fin caso 1
	//comprueba comando readf
	if (strcmp(command,"readf") == 0){					
		printf("args[name] >>");						
		scanf("%s",arg);								 
		readf(arg);										
		myShell();										
	}//fin caso 2
	//comprueba comando lsf
	if (strcmp(command,"lsf") == 0){
		printf("Root de MySis\n");
		lsf();
	}
//otros comandos
//comprueba comando dtms
	if (strcmp(command,"dtms") == 0)
	{
		dtms();
		myShell();
	}//fin caso 2.5
//limpia pantalla
	if (strcmp(command, "clean") == 0){					
		system("clear");
		myShell();
	}//fin caso 3
//despliega secciones de la documentacion
	if (strcmp(command,"help") == 0){					
		help();											//despliega help.ric
		fflush(stdin);
		myShell();										
	}//fin caso 4
	if (strcmp(command,"info") == 0){					 
		info();											//despliega info.ric
		myShell();										
	}//fin caso 5
//verifica salida
	if (strcmp(command,"quit") == 0){					
			return;										//cierra myShell; regresa control a main y finaliza ejecucion
	}//fin caso final
//cualquier otro caso
	else{												//comando invalido. Regresa control a myShell
		puts("No Valid Command");
		myShell();	
	}//fin caso noValidCommand

}//fin shell