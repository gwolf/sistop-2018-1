/**
*Archivo de cabecera donde se declaran y definen las
*funciones que manipularan archivos de disco en el
*microsistema de archivos
*/

//prototipos de funcion
void newfil(char *arg);		//crea archivo
void readf(char *arg);			//despliega contenido
void lsf();			//clona arcivo
void dtms();		//muestra el contenido completo del microsistema

/**
@funtion: newfil
@param: recibe una cadena que es el nombre del archivo que se creara
@descrition: crea archivo a partir de la cadena (nombre) recibida en el
			parametro. Se usa un buffer para almacenar la cadena que 
			ingrese el usuario desde cosola.
*/
void newfil(char *arg){
	FILE *fichero;								//apuntador a struc FILE
	char *buffer;								//buffer para almacenar cadenas recibidas
	buffer = malloc(255*sizeof(char));			//reserva memoria para el buffer
	printf(">> %s\n",arg);
	fichero = fopen("raiz","a+");				//abre fichero en modo adicion al final
	scanf("%s",buffer);								//recibe cadena desde teclado
	fprintf(fichero, "\n%s\n%s\n}\n",arg,buffer);	//etiqueta para identificar seccion, y aniade texto introducido
	fclose(fichero);							//cierra flujo del fichero
}//fin newfil
/**
@funtion: readf
@param: recibe el nombre del fichero a leer
@description: Solamente abre un fichero existente y despliega su contenido
			en consola
*/
void readf(char *arg){
	FILE *fichero;								//apuntador a struc FILE
	char *buffer;								//buffer para almacenar cadenas recibidas
	buffer = malloc(255*sizeof(char));			//reserva memoria para el buffer
	printf(">> %s\n",arg);
	fichero = fopen("raiz","r");				//abre fichero en modo lectura
	while(!feof(fichero)){						//comienza a leer el fichero completo
		fscanf(fichero,"%s\n",buffer);				//recibe cadena desde fichero y almacena en Buffer
		if (strcmp(buffer,arg) == 0)			//verifica que el buffer contenga la etiqueta del bloque que se desplegara
		{
			while (strcmp(buffer,"}") != 0){		//no se haya llegado al final del bloque
				fscanf(fichero,"%s\n",buffer);				//recibe cadena desde fichero y almacena en Buffer
				printf("%s\n",buffer);				//vacia buffer en pantalla
			}//fin while	
		}//fin if
	}//fin while
	fclose(fichero);							//cierra flujo del fichero
}//fin readf

/**
@funtion: destroy
@param: recibe el nombre del fichero eliminar
@description: elimina archivo seleccionado. Pide confirmacion y notifica el resultado
			de la operacion
*/

void lsf(){
	FILE *fichero;								//apuntador a struc FILE
	char *buffer;								//buffer para almacenar cadenas recibidas
	buffer = malloc(255*sizeof(char));			//reserva memoria para el buffer
	fichero = fopen("raiz","r");				//abre fichero en modo lectura
	while(!feof(fichero)){						//comienza a leer el fichero completo
		fgets(buffer,255,fichero);			//recibe cadena desde fichero y almacena en Buffer
		if (strstr(buffer,".msf") != NULL)		//verifica que sea un archivo (posee extencion msf
			printf("%s\n",buffer);				//imprime la cadena contenida en Buffer
	}//fin while
	fclose(fichero);							//cierra flujo del fichero
}

void dtms(){
	FILE *fichero;								//apuntador a struc FILE
	char *buffer;								//buffer para almacenar cadenas recibidas
	fichero = fopen("raiz","r");			//abre fichero en modo lectura
	buffer = malloc(255*sizeof(char));			//reserva memoria para el buffer
	while (!feof(fichero)){						//no ha llegado al final del archivo
		fgets(buffer,255,fichero);				//recibe cadena desde fichero y almacena en Buffer
		printf("%s",buffer);					//vacia buffer en pantalla
	}
	fclose(fichero);	
}