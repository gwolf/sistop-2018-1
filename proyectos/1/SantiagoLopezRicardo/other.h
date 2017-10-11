/**
*Archivo de cabecera que solo ejecuta los mudulos para 
*los comandos extras
*/
//prototipos de funcion
void help();	//despliega fichero Help.txt
void info();	//despliega Fichero info.txt

void help(){
	puts(">> help ");
	FILE *fichero;								//apuntador a struc FILE
	char *buffer;								//buffer para almacenar cadenas recibidas
	fichero = fopen("help.ric","r");			//abre fichero en modo lectura
	buffer = malloc(255*sizeof(char));			//reserva memoria para el buffer
	while (!feof(fichero)){						//no ha llegado al final del archivo
		fgets(buffer,255,fichero);				//recibe cadena desde fichero y almacena en Buffer
		printf("%s",buffer);					//vacia buffer en pantalla
	}
	fclose(fichero);
}//fin help

void info(){
	puts(">> info ");
	FILE *fichero;								//apuntador a struc FILE
	char *buffer;								//buffer para almacenar cadenas recibidas
	fichero = fopen("info.ric","r");			//abre fichero en modo lectura
	buffer = malloc(255*sizeof(char));			//reserva memoria para el buffer
	while (!feof(fichero)){						//no ha llegado al final del archivo
		fgets(buffer,255,fichero);				//recibe cadena desde fichero y almacena en Buffer
		printf("%s",buffer);					//vacia buffer en pantalla
	}
	fclose(fichero);
}//fin info