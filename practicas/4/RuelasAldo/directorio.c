#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <string.h>

void error(const char *s);
void procesoArchivo(char *archivo);

int main()
{
  DIR *dir;
  struct dirent *ent;
  dir = opendir (".");
  if (dir == NULL)
    error("No puedo abrir el directorio");
  while ((ent = readdir (dir)) != NULL)
    {
      if ( (strcmp(ent->d_name, ".")!=0) && (strcmp(ent->d_name, "..")!=0) )
    {
      procesoArchivo(ent->d_name);
    }
    }
  closedir (dir);

  return EXIT_SUCCESS;
}

void error(const char *s)
{
  perror (s);
  exit(EXIT_FAILURE);
}

void procesoArchivo(char *archivo)
{
  FILE *fich;
  long ftam;
  fich=fopen(archivo, "r");
  if (fich)
    {
      fseek(fich, 0L, SEEK_END);
      ftam=ftell(fich);
      fclose(fich);
      printf ("%30s (%ld bytes)\n", archivo, ftam);
    }
  else
    printf ("%30s (No info.)\n", archivo);
}

