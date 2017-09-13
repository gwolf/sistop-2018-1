#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>
#include <string.h>

unsigned char tipoConStat(char *fname); //nos devuelve el tipo de archivo utilizando stat()
long tamArchivo(char *fname); //función para calcular el tamaño del archivo
void tipoDeArchivo(char *ruta, struct dirent *ent); //función para analizar el archivo
int funcionPrincipal(); //Funcion que profundizará un nivel máximo

int aux = 0;
char *rutaDirectorio;
DIR *dir; //apuntador de tipo DIR para guardar lo que regrese opendir()  

int main(int argc, char *argv[]){
  
  if (argc != 2){ //para asegurar que tenga dos argumentos
    printf("Se debe pasar la ruta como parametro!!\n");
    return EXIT_FAILURE;
  }
  rutaDirectorio = argv[1];
  dir = opendir (rutaDirectorio); //le pasamos el path a opendir y se lo asignamos al apuntador dir

  if (dir == NULL) //checamos que sí se puede abrir 
    printf("No puedo abrir el directorio");

  
  
  funcionPrincipal(); 
  return EXIT_SUCCESS;
} 

int funcionPrincipal(){    
  aux = 0;
  struct dirent *ent; //en la estructura dirent se encuentra la información de dentro del directorio
      
  while ((ent = readdir (dir)) != NULL){ //ciclo para leer todo lo que tenga el directorio
    if ( (strcmp(ent->d_name, ".") != 0) && (strcmp(ent->d_name, "..")!= 0) ){ //Cualquier cosa que no sea '.' y '..' lo analizará
      tipoDeArchivo(rutaDirectorio, ent); //función que analizará el archivo
      // if (ent->d_type == DT_DIR) { 
      //   aux = 1; 
      //   sprintf(rutaDirectorio,"./%s", ent->d_name);
      // }
    }    
  }
  // if(aux == 1){
  //   printf("%s\n", rutaDirectorio );
  //   dir = opendir(rutaDirectorio);
  //   funcionPrincipal();
  // }
  closedir (dir);
  return EXIT_SUCCESS;
}

long tamArchivo(char *nombreArchivo){
  
  FILE *archivo; //apuntador de tipo archivo 
  long ftam; //entero largo para guardar el tamaño del archivo
  archivo = fopen(nombreArchivo, "r"); //abrimos el archivo en modo de lectura
  if (archivo) {
    fseek(archivo, 0L, SEEK_END); //fseek() es un puntero para localizarte hasta
    ftam = ftell(archivo); //ftell() te da la posición en el archivo, por lo tanto te dará el tamaño de lo que contenga
    fclose(archivo);
  }
  else
    printf("Tamanio desconocido");
  return ftam;
}

void tipoDeArchivo(char *ruta, struct dirent *ent){
  
  long ftam;
  char *nombrecompleto;
  char strtam[20];
  char strtipo[50]="";
  static unsigned char d_types[7]={DT_REG, DT_DIR, DT_FIFO, DT_SOCK, DT_CHR, DT_BLK, DT_LNK};
  static char* tipos[7]={"archivo normal", "directorio", "tubería nombrada (piped file)", "socket local", "dispositivo de caracteres", "dispositivo de bloques", "link simbolico"};
  /*Tipos de archivo obtenidos de http://www.gnu.org/software/libc/manual/html_node/Directory-Entries.html*/
  int i;
  int tmp;
  unsigned char tipo;
  /* Sacamos el nombre completo con la ruta del archivo */
  tmp = strlen(ruta);
  nombrecompleto = malloc(tmp+strlen(ent->d_name)+2); /* Sumamos 2, por el \0 y la barra de directorios (/) xq no sabemos si falta */
  if (ruta[tmp-1] == '/') //si tiene el slash al final, no le ponemos nada
    sprintf(nombrecompleto,"%s%s", ruta, ent->d_name);
  else
    sprintf(nombrecompleto,"%s/%s", ruta, ent->d_name);

  /* Calcula el tamaño */
  ftam = tamArchivo(nombrecompleto);
  if (ftam >= 0)
    sprintf(strtam, "%ld bytes", ftam);
  else
    strcpy(strtam, "desconocido");

  /* A veces ent->d_type no nos dice nada, eso depende del sistema de archivos que estemos */
  /* mirando, por ejemplo ext*, brtfs, sí nos dan esta información. Por el contrario, nfs */
  /* no nos la da (directamente, una vez que hacemos stat sí lo hace), y es en estos casos donde probamos con stat() */
  tipo=ent->d_type;
  if (tipo == DT_UNKNOWN)
    tipo = tipoConStat(nombrecompleto); //ejecutamos stat() si no funciona el d_type "a secas"
  if (tipo != DT_UNKNOWN) 
    {
      i = 0;
      /*Hacemos la relacion entre valor obtenido por d_type o stat(), y la descripción del mismo*/
      while ( (i < 7) && (tipo != d_types[i]) ) 
      ++i;
      if (i < 7)
        strcpy(strtipo, tipos[i]);
    }

  /* si no le damos valor a  strtipo, no encontramos tipo de archivo*/
  if (strtipo[0] == '\0')
    strcpy(strtipo, "Tipo de archivo desconocido");

  printf ("%30s -> %30s\t%s \n", ent->d_name, strtam, strtipo);
  // printf("%d\n", d_types[0] );
  free(nombrecompleto);
}

unsigned char tipoConStat(char *nombreArchivo){ 
  struct stat sdata;
  if (stat(nombreArchivo, &sdata) == -1) //función stat() acompañado de struct stat para saber el tipo de archivo
      return DT_UNKNOWN;
  switch (sdata.st_mode & S_IFMT){ //obteniendo el tipo de archivo con st_mode
    case S_IFREG:  
      return DT_REG;
    case S_IFDIR:  
      return DT_DIR;
    case S_IFCHR:  
      return DT_CHR;
    case S_IFBLK:  
      return DT_BLK;
    case S_IFIFO:  
      return DT_FIFO;
    case S_IFLNK:  
      return DT_LNK;
    case S_IFSOCK: 
      return DT_SOCK;
    default:       
      return DT_UNKNOWN;
    /* Obtenidos de http://man7.org/linux/man-pages/man7/inode.7.html */
  }
}