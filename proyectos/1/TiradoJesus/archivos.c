/*TIRADO PÉREZ JOSÉ JESÚS 
***sistema de archivos***/
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h> 
#include <ctype.h>

#define TAMCAD 50  //definicion del tamaño de cadena que se va a manejar
char contenido[TAMCAD]; 
int numEstructuras = 0; //es el contador de estructuras, indica cuantos archivos tenemos.

struct data{ //cada archivo cuenta con dos caracteristicas, un nombre y un contenido
	char name[70],archivo[200];
};

void cortaPalabra(char * original, char * parte, int tam, int funcion);
int instruccion(char cadena[TAMCAD]);

/* se ejecutará el ciclo while hasta que el usuario escriba "salir" */
int main(){ 
	struct data *pointer; //creando un apuntador a estructura data
	pointer=(struct data*)calloc(50,sizeof(struct data));
	//malloc devuelve un apuntador al espacio asignado, 50 apuntadores del tamaño de la estructura data
	//MALLOC NO FUNCIONO PORQUE NECESITAMOS UN "ARREGLO" DE APUNTADORES A UN ARREGLO DE ETSRUCTURAS!! USAR CALLOC!!! OBVIO :D
	struct user *puntero;
	//variable opcion para el inicio, i como contador para señalar en que estructura vamos struct[1], struct[2]...struct[n]
	
	int opcion=0,contadorC=1; 
	int fin=0;
	int j=0;
	char comando[TAMCAD];  
	char cadFin[]="exit";
	system("clear");
	while(fin==0) {
        printf("jesusTirado@sistemArchivos:~$ ");//este es el prompt 
		scanf("\n%[^\n]",comando); // Escaneamos la cadena entera hasta que pulsa intro
		while ((comando[j]==cadFin[j])&&(comando[j]!='\0')&&(cadFin[j]!='\0')) // se comparan las cadenas para ver si se tecleó "exit"
			j++;	
		if ((comando[j]==cadFin[j])&&(comando[j]=='\0')&&(cadFin[j]=='\0')){
			exit(0);
		}
		else{
			opcion=instruccion(comando);
			switch(opcion){
				case(1):{    // se genera una estructura con el nombre del archivo que estamos definiendo
					strcpy(pointer[numEstructuras].name, contenido); 
					numEstructuras++; 
					break;
				}
				case(2):{   //se realiza una busqueda del nombre del achivo que se quiere eliminar
					char bandera='f', archivo[50];
					int cont=0;
					while((bandera=='f') && (cont<numEstructuras)){
						strcpy(archivo, pointer[cont].name);
						if(strcmp(archivo,contenido)==0){
							bandera = 'v';
							//printf("se borra..\n");
							//para borrar el archivo se tiene que reposicionar la estructura a partir del seleccionado
						    for (int i = cont; i < numEstructuras; i++)
						    {
						    	strcpy(pointer[i].name, pointer[i + 1].name);
						    	strcpy(pointer[i].archivo, pointer[i + 1].archivo);
						    }
						    //reindicamos el tamaño de nuestro arreglo de archivos reduciendolo en 1
						    numEstructuras--;
						}
						cont++;
					}
					if(bandera=='v')
						printf("archivo eliminado...\n");
					else 
						printf("no se localiza el archivo seleccionado\n");
					/*//indicadores para pruebas ;)
					printf("%s\n", contenido);
					printf("%d\n",cont );
					printf("%s\n", archivo);
					*/
					break;
				}
				case(3):{
					int cont=0;
					char tipo[20];
					while(cont<numEstructuras){
						if (strrchr(pointer[cont].name,'.')==0){	//lista el directorio, si el archivo no fue definido con 
							strcat(pointer[cont].name, ".txt"); 	//alguna extension entonces se define como un .txt
						}else {
							strcpy(tipo,strrchr(pointer[cont].name,'.'));
						}
						printf("%s\n",pointer[cont].name);
						cont++;
					}
					break;
				}
				case(4):{
					char bandera='f', archivo[200];
					int cont=0;
					while((bandera=='f') && (cont<numEstructuras)){
						strcpy(archivo, pointer[cont].name);
						if(strcmp(archivo,contenido)==0)
							bandera = 'v';
						cont++;
					}
					if(bandera=='v'){
						system("clear");
						printf("\t\t--> MODO ESCRITURA <--\n\n"); //se habilita la estructura seleccionada para poder escribir en el archivo
						scanf("\n%[^\n]",pointer[cont].archivo);
						getchar();
					}else 
						printf("no se localiza el archivo seleccionado\n");
					//printf("\n%s\n",pointer[cont].archivo);
					break;
				}
				case(5):{
					char bandera='f', archivo[200];
					int cont=0;
					while((bandera=='f') && (cont<numEstructuras)){
						strcpy(archivo, pointer[cont].name);
						if(strcmp(archivo,contenido)==0)
							bandera = 'v';
						cont++;
					}
					if(bandera=='v'){
						system("clear");
						printf("\t\t--> MODO LECTURA <--\n\n"); //se habilita la estructura seleccionada para poder leer el contenido del archivo
						printf("%s\n\n",pointer[cont].archivo);
						getchar();
					}else 
						printf("no se localiza el archivo seleccionado\n");
					//printf("\n%s\n",pointer[cont].archivo);
					break;
				}
				case(6):{
					char bandera='f', archivo[200], archivoComp[200];
					int cont=0;
					while((bandera=='f') && (cont<numEstructuras)){
						strcpy(archivo, pointer[cont].name);
						if(strcmp(archivo,contenido)==0)
							bandera = 'v';
						cont++;
					}
					if(bandera=='v'){
						system("clear");
						printf("\t\t--> AGREGAR DATOS <--\n\n"); //se habilita la estructura seleccionada para poder leer el contenido del archivo
						printf("%s\n\n",pointer[cont].archivo);
						scanf("\n%[^\n]",archivoComp);
						strcat(pointer[cont].archivo, archivoComp);
						getchar();
					}else 
						printf("no se localiza el archivo seleccionado\n");
					//printf("\n%s\n",pointer[cont].archivo);
					break;
				}
				case(7):{
					system("clear");
					printf("\t\tMANUAL DE USO:\n");
					printf("el sistema consiste en administrar archivos, permitiendo las siguientes acciones: \n");
					printf("\t- crear\n\t- listar\n\t- escribir\n\t- leer\n\t- agregar\n");
					printf("para poder ejecutar cualquiera de las acciones es necesario seguir la siguiente estructura de funciones:\n");
					printf("\n\t- new nombre.tipo --> para crerar un nuevo archivo, sino se especifica la extension del archivo el sistema genera un archivo .txt\n");
					printf("\n\t- list --> para listar los elementos del directorio, la funcion no requiere argumentos.\n");
					printf("\n\t- errase nombre.tipo --> para eliminar algun archivo del directorio, es necesario especificar nombre y tipo de archivo como argumento.\n");
					printf("\n\t- edit -w nombre.tipo --> abre el editor en modo escritura, es necesario especificar -w para indicar el modo escritura asi como nombre y tipo de archivo. Despliega una pantalla donde queda habilitada la escritura en el archivo y para salir solo es necesario presionar la tecla ENTER\n");
					printf("\n\t- edit -w nombre.tipo --> abre el editor en modo escritura, es necesario especificar -w para indicar el modo escritura asi como nombre y tipo de archivo. Despliega una pantalla donde queda habilitada la escritura en el archivo y para salir solo es necesario presionar la tecla ENTER\n");
					printf("\n\t- edit -r nombre.tipo --> abre el editor en modo lectura, es necesario especificar -r para indicar el modo lectura asi como nombre y tipo de archivo. Despliega una pantalla con el contenido del archivo y para salir solo es necesario presionar la tecla ENTER\n");
					printf("\n\t- edit -a nombre.tipo --> abre el editor para agregar informacion, es necesario especificar -a para indicar que se quiere agregar y no sobrescribir asi como nombre y tipo de archivo. Despliega una pantalla donde podremos agregar informacion al archivo y para salir solo es necesario presionar la tecla ENTER\n");
					printf("\n\t- exit --> para salir del programa, termina la ejecucion\n\n\n");
					break;
				}
			}

			//printf("%d\n",instruccion(comando)); // sino se ejecutará el comando 
			//printf( "%s\n", contenido);
		}
		/*printf("%s\n",pointer[0].name );
		printf("%s\n",pointer[1].name );
		printf("%s\n",pointer[2].name );
		*/
	}

	exit(0);//finaliza el programa 
	return(0);
}

int instruccion(char cadena[TAMCAD]){
	char cadNuevo[]="new ";
	char cadBorrar[]="errase ";
	char cadLista[]="list ";
	char cadEditarEscribir[]="edit -w ";
	char cadEditarLeer[]="edit -r ";
	char cadEditarAnadir[]="edit -a ";
	int j=0, funcion=0;
	if (cadena[0]=='n' && cadena[1]=='e' && cadena[2]=='w' && cadena[3]==' '){
		cortaPalabra(cadena,cadNuevo,4,1);
		funcion = 1;
	}else if (cadena[0]=='e' && cadena[1]=='r' && cadena[2]=='r' && cadena[3]=='a' && cadena[4]=='s' && cadena[5]=='e' && cadena[6]==' '){
		cortaPalabra(cadena,cadBorrar,7,2);
		funcion = 2;
	}else if (cadena[0]=='l' && cadena[1]=='i' && cadena[2]=='s' && cadena[3]=='t'){
		funcion = 3;
	}else if (cadena[0]=='e' && cadena[1]=='d' && cadena[2]=='i' && cadena[3]=='t' && cadena[4]==' ' && cadena[5]=='-' && cadena[6]=='w' && cadena[7]==' '){
		cortaPalabra(cadena,cadEditarEscribir,8,4);
		funcion = 4;
	}else if (cadena[0]=='e' && cadena[1]=='d' && cadena[2]=='i' && cadena[3]=='t' && cadena[4]==' ' && cadena[5]=='-' && cadena[6]=='r' && cadena[7]==' '){
		cortaPalabra(cadena,cadEditarLeer,8,5);
		funcion = 5;
	}else if (cadena[0]=='e' && cadena[1]=='d' && cadena[2]=='i' && cadena[3]=='t' && cadena[4]==' ' && cadena[5]=='-' && cadena[6]=='a' && cadena[7]==' '){
		cortaPalabra(cadena,cadEditarAnadir,8,6);
		funcion = 6;
	}else if (cadena[0]=='m' && cadena[1]=='a' && cadena[2]=='n'){
		funcion = 7;
	}else {
		printf("no se reconoce el comando\n-->\tman : para conocer las instrucciones\n");
	}
	return funcion;
}

void cortaPalabra(char * original, char * parte, int tam, int funcion){
    //char original[] = "funciona chido";
    //char parte[] = "funciona ";
    char nueva[ 21 ];
    int posicion = strlen( original ) - strlen( strstr( original, parte ) );
    // copiar la primera parte
    for( int a = 0; a < posicion; a++ )
        nueva[ a ] = original[ a ];
    // copiar la segunda parte
    for( int a = posicion; a < strlen( original ); a++ )
        nueva[ a ] = original[ a+tam ]; 
    nueva[ 20 ] = '\0';
    //printf( "%s\n", nueva );
    strcpy(contenido,nueva);
}
