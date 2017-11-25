/*TIRADO PÉREZ JOSÉ JESÚS 
***monitor del sistema en interprete de comandos***/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> //necesaria para utilizar execvp
#include <fcntl.h>  //se necesita para especificar el tipo de uso que se le va a dar a los archivos 
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h> 
#include <signal.h> //para las señales :) 
#include <pthread.h> // uso de hilos
#include <time.h> //nesearia para manejar la hora del sistema

#define TAMCAD 50  //definicion del tamaño de cadena que se va a manejar

void historial(void *);
void promptSelec(int tipo);
void defEntrada(char archivo[TAMCAD]);
void defSalida(char archivo[TAMCAD]);
void defError(char archivo[TAMCAD]);
void tuberias(char* primerInstPuntero1[TAMCAD], char* segunInstPuntero[TAMCAD]);
void nuevoProceso(char* primerInstPuntero[TAMCAD]);
void instruccion(char cadena[TAMCAD]);

/* se ejecutará el ciclo while hasta que el usuario escriba "salir" */
int main(){ 
	pthread_t thread;
	int fin=0,i,salida=dup(1),entrada=dup(0),error=dup(2);
	int j=0;
	int cadIigual=0;
	char comando[TAMCAD];  
	char cadFin[]="salir";
	while(fin==0) {
        close(1); //se cierra la salida que tenga para que la nueva instruccion aparezca en terminal.
        dup(salida); // Asigno la salida estándar
        close(0); 
        dup(entrada); 
        close(2);
        dup(error);
		//int sen = signal(SIGINT,NULL);
		//if (sen == 0)
		//	promptSelec(1);
		/* muchas pruebas para las señales :(
		signal(SIGIO, promptSelec);
		signal(SIGPWR, promptSelec);
		signal(SIGSYS, promptSelec); // Imprimimos el prompt
		*/
		signal(SIGINT, promptSelec);
		promptSelec(0);
		scanf("\n%[^\n]",comando); // Escaneamos la cadena entera hasta que pulsa intro
		while ((comando[j]==cadFin[j])&&(comando[j]!='\0')&&(cadFin[j]!='\0')) // se comparan las cadenas para ver si se tecleó "salir"
			j++;	
		if ((comando[j]==cadFin[j])&&(comando[j]=='\0')&&(cadFin[j]=='\0')) 
			exit(0);
		if (pthread_create(&thread, NULL, (void *) historial, &comando) != 0)//nuevo hilo generado para ir capturando las instrucciones ingresadas
			perror("pthread_create"), exit(1); 
		instruccion(comando); // sino se ejecutará el comando 
	}
	exit(0);//finaliza el programa 
	return(0);
} 
/*funcion de prueba para que las señales modifiquen el PROMPT
/*
int senal(){
	signal(SIGIO, printf(PROMP31));
	signal(SIGPWR, promptSelec(2));
	signal(SIGSYS, promptSelec(3));
	return 0;
}
*/ 
/*aqui se hace la seleccion del prompt correspondiente, si no hay señal 
se imprime siempre el prompt original*/
void promptSelec(int tipo){
	switch(tipo) {
    	case 2:
        	printf("monitor@segundoUsuario:~$");//para la señal generada con "^C" 
        	break;
        default :
      		printf("monitor@sistop:~$");//este es el prompt original 
   }
}

/* el parámetro de entrada es el nombre del archivo que seá la entrada */
void defEntrada(char archivo[TAMCAD]){ 
	char *archivoP;
	int fd;  
	archivoP = archivo; // Puntero a la archivo
	fd = open (archivoP,O_RDONLY); // Asigno a la entrada el fichero
	close (0); // Cierro la entrada estándar	
	dup(fd);	
} 

/* el parámetro de entrada es el nombre del archivo que será la salida */
void defSalida(char archivo[TAMCAD]){ 
  	char *archivoP;
  	archivoP = archivo; // Puntero a la archivo
  	close (1); // Cierro la salida estándar		
  	open (archivoP,O_CREAT | O_WRONLY); // Asigno a la salida el fichero
} 

/* el parámetro de entrada es el nombre del archivo que será la salida */
void defError(char archivo[TAMCAD]){ 
  	char *archivoP;
  	archivoP = archivo; // Puntero a la archivo
  	//printf("ejecutando\n");
  	close (2); // Cierro la salida estándar		
  	open (archivoP,O_CREAT | O_WRONLY); // Asigno a la salida el fichero
} 

/* creará un hijo y será el encargado de ejecutar las 2 instrucciones pasados por tubería,
el hijo ejecutará la primera intrucción y le arrojará lo obtenido al padré para que pueda 
realizar la segunda instrucción.*/
void tuberias(char* primerInstPuntero[TAMCAD], char* segunInstPuntero[TAMCAD]){ 
  	int fd[2],estado;
	pid_t hijo; 
	hijo=fork();
	if (hijo==-1) 
		printf("ERROR Creacion de proceso"); 
	else if (hijo==0) {
		pipe(&fd[0]); //crea la tuberia
   		if (fork()!=0) {
      		close(fd[0]); //cerramos el lado de lectura de tuberia
      		close(1);
      		dup(fd[1]); /* extremo de salida  */
      		close(fd[1]);
      		execvp(primerInstPuntero[0],primerInstPuntero);/*execvp para ejecutar el comando que etará dado por un apuntado a la cadena 
      											para la primera instruccion*/
      		perror("no se encontro la orden");
			exit(estado);
   		}else {
      		close(fd[1]);
      		close(0);
      		dup(fd[0]);/* extremo de salida  */
      		close(fd[0]);
	 		execvp(segunInstPuntero[0],segunInstPuntero);/*execvp para ejecutar el comando que etará dado por un apuntado a la cadena 
      											para la primera instruccion*/
      		perror("no se encontro la orden");
			exit(estado);
      	}
	}else
		hijo=wait(&estado);
	//printf("%d\n", wait(&estado));
} 

/* en caso de no haber tuberia solo se creará un nuevo proceso 
encargado de ejecutar la instrucción.
La intención es generar un nuevo proceso que realice la instrucción,
el proceso padre tiene que esperar a que la instruccion termine su ejecución y una vez finalizado el proceso hijo 
se vielve a imprimir el prompt*/
void nuevoProceso(char* primerInstPuntero[TAMCAD]){	
	int estado;
	pid_t hijo; 
	hijo=fork();
	if (hijo==-1)
		printf("ERROR Creacion de proceso"); 
	else if (hijo==0) {
		execvp(primerInstPuntero[0],primerInstPuntero);
		perror("no se encontro la orden");
		//exit(estado);
	}else{ 
		hijo=wait(&estado);
	}  
} 

/*FUNCIÓN QUE MANEJA LA CADENA PASADA COMO PARÁMETRO     
  NOTA PARA EL PROFE: en el proyecto pasado me hizo ver ese mal sentido que tengo para el uso de cadenas 
  					y parece que sigo insistiendo usando mi dañado razonamiento :( :( :( 
consiste en identificar y seccionar la cadena que le es pasada como paramatro, ubicando si el ella se 
contiene el uso de tuberias o se define otro direccionamiento (cadena que se pasará a otra funcion para poder crear el archivo destino)
  */
void instruccion(char cadena[TAMCAD]){ 
	char primerInst[TAMCAD][TAMCAD]; 				
	char segunInst[TAMCAD][TAMCAD];
	char cambiaSalida[TAMCAD];
	char cambiaEntrada[TAMCAD];
	char *primerInstPuntero[TAMCAD]; 
	char *segunInstPuntero[TAMCAD];
	int ejecutar=0;
	int i,j,k,usaTuberia;		
	primerInstPuntero[0] = NULL;					
	segunInstPuntero[0] = NULL;
	usaTuberia = 0;
 	k = 0;
    i = 0;
	while(cadena[i] != '\0' && cadena[i] != '|' && cadena[i] != '>' && cadena[i] != '2'){  
		/* Con este for recorremos la cadena por completo, y la vamos
		almacenando	en una variable que consiste en un arreglo bidimensional de tipo char */
		for(j=0; cadena[i] != ' ' && cadena[i] != '\0' && cadena[i] != '|'  && cadena[i] != '>' && cadena[i] != '<';j++) { 
			primerInst[k][j] = cadena[i];
			i++;
		}
		/* Comprobamos si la cadena salió del ciclo por encontrar un espacio...
	 	en tal caso se incrementa la i, ya que sino entraría en un ciclo infinito*/
	 	if (cadena[i] == ' ')
	 		 i++;
		/* Asignamos el terminador de cadena a cada instruccion que es leído */
		primerInst[k][j] = '\0';
		/* Y finalmente una vez creada la cadena, se la pasamos al puntero 
		primerInstPuntero que será el que se ejecute con la función execvp */
		primerInstPuntero[k] = primerInst[k];
		k++;
		if (cadena[i] == '<') { 
			i++;
			if (cadena[i] != ' ') 
				ejecutar=1;
			else { 
				i++;
				for(j=0; cadena[i] != '\0' && cadena[i] != ' ' && cadena [i] != '|' && cadena [i] != '>'; j++){
					cambiaEntrada[j] = cadena[i];
					i++;
				}
				cambiaEntrada[j] = '\0';
				if (cadena[i] != '\0') i++;
				defEntrada(cambiaEntrada); /*se manda a llamar a la funcion que define la entrada 
											ya que se encontro un "<" en la cadena*/
			}
		}
	} 
	primerInstPuntero[k] = NULL; // Asignamos NULL al último instruccion a ejecutar para el EXECV
   	/* Si encuentra un > cortará la cadena que será el fichero que quiere utilizar
	para la salida */
	if (cadena[i] == '>') {
		i++;
		if (cadena[i] != ' ') 
			ejecutar=1;
		else{
			i++;
			for(j=0; cadena[i] != '\0';j++) {
				cambiaSalida[j] = cadena[i];
				i++;
			}
			cambiaSalida[j] = '\0';
			defSalida(cambiaSalida);/*se manda a llamar a la funcion que define la salida 
											ya que se encontro un ">" en la cadena*/	
		}
	}
	/* Si encuentra un 2> cortará la cadena que será el fichero que quiere utilizar
	para la salida del error*/
	if (cadena[i] == '2' && cadena[i+1] == '>') {
		i++;
		i++;
		if (cadena[i] != ' ') 
			ejecutar=1;
		else{
			i++;
			for(j=0; cadena[i] != '\0';j++) {
				cambiaSalida[j] = cadena[i];
				i++;
			}
			cambiaSalida[j] = '\0';
			defError(cambiaSalida);/*se manda a llamar a la funcion que define la salida 
											ya que se encontro un "2>" en la cadena*/	
		}
	}	
	
    /* si encuentra el signo | significa que utilizará tubería y separa las cadenas en dos comandos */
	if (cadena[i] == '|') {
		k=0;
		i++;
		if (cadena[i] != ' ') 
			ejecutar=1;
		else { 
			i++;
			usaTuberia = 1;
			while(cadena[i] != '\0' && cadena[i] != '>'){ 
				for(j=0; cadena[i] != ' ' && cadena[i] != '\0' && cadena[i] != '>';j++) {
					segunInst[k][j] = cadena[i];
					i++;
				}
				if (cadena[i] == ' ' )
					i++;
				segunInst[k][j] = '\0';
				segunInstPuntero[k] = segunInst[k];
				k++;
			}
			segunInstPuntero[k] = NULL;
		}	
	}
	/* Comprobamos si la variable ejecutar tiene valor 0 o 1, si tiene valor 0
	el programa se ejecutará correctamente, si tiene valor 1 significa que 
	mientras analizaba alguna de las cadenas ha encontrado un error de sintaxis
	y mostrará en pantalla un mensaje de error. */
	if(ejecutar == 0) {
		if (usaTuberia==0) // si la instruccion NO requiere el uso de tuberias se llama a la funcion que creará un nuevo proceso
			nuevoProceso(primerInstPuntero);
		else //so la instruccion SI requiere el uso de tuberias la funcion correspondiente ligará dos procesos 
			tuberias(primerInstPuntero,segunInstPuntero);
	}else
		printf("error de sintaxis!\n");
}

/*se implementara el historial en un hilo*/
void historial(void *arg)
{
	time_t tiempo = time(0);
    struct tm *tlocal = localtime(&tiempo);
    char output[128];
    strftime(output, 128, "%d/%m/%y %H:%M:%S", tlocal); //formato de fecha y hora
    FILE* fichero;
    fichero = fopen("historial.txt", "a"); //se crea el archivo del historial
    fprintf (fichero, "%s >> %s\n",output,arg); //imprimimos dentro del archivo el parámetro de la inst junto a la hora correspondiente
    fclose(fichero);
}

