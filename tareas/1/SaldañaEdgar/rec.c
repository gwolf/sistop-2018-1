#include <stdio.h>
#include <dirent.h>
#include <string.h>
#include <sys/types.h>
#include <stdlib.h>

#define KNRM  "\x1B[0m"
#define KYEL  "\x1B[33m"
#define KCYN  "\x1B[36m"

int openDir(char*, int);
char *getFullName(char*, struct dirent*);
unsigned char getFileType(char*, struct dirent*);

char salto[500] = "";
int auxprof = 0;
	
int main(int argc, char *argv[]) {

	if (argc != 3) {
		printf("\nEjecuta el programa de esta forma:\n");
		printf("./rec <directorio> <profundidad>\n");
		return 1;
	}

	auxprof = strtol(argv[2], NULL, 10);
	openDir(argv[1], auxprof);

	return 0;
}

int openDir(char* ruta, int profundidad){

	DIR *dir;
	struct dirent *ent;
	char *nombrecompleto;
	unsigned char tipo;
	

	dir = opendir(ruta);

	if (dir == NULL){
		printf("%s%s\n", salto, "No se pudo abrir el directorio");
		return 1;
	}

	while ((ent = readdir(dir)) != NULL){

		if ((strcmp(ent->d_name, ".")!=0) && (strcmp(ent->d_name, "..")!=0))
		{
			nombrecompleto = getFullName(ruta, ent);
			tipo = getFileType(nombrecompleto, ent);
			if (tipo == 4){
				printf("%s%12s",KYEL,"directorio--");
				printf("\t%s%s%s\n", salto, KNRM, ent -> d_name);
				
				if(profundidad > 0){ //condición de paro
					//avanza gráficamente la profundidad del árbol
					strcat(salto,"       ");
					profundidad --;
					openDir(nombrecompleto, profundidad);
					profundidad = auxprof;
					strcat(salto,"\b\b\b\b\b\b\b");
				}
			}
			if (tipo == 8){
				printf("%s%9s",KCYN,"archivo--");
				printf("\t%s%s%s\n", salto, KNRM, ent -> d_name);
			}
		}
	}
	return 0;
}

char *getFullName(char *ruta, struct dirent *ent)
{
  char *nombrecompleto;
  int tmp;

  tmp=strlen(ruta);
  nombrecompleto=malloc(tmp+strlen(ent->d_name)+2);
  if (ruta[tmp-1]=='/')
    sprintf(nombrecompleto,"%s%s", ruta, ent->d_name);
  else
    sprintf(nombrecompleto,"%s/%s", ruta, ent->d_name);
 
  return nombrecompleto;
}
unsigned char getFileType(char *nombre, struct dirent *ent)
{
  unsigned char tipo;
  tipo=ent->d_type;
  return tipo;
}
