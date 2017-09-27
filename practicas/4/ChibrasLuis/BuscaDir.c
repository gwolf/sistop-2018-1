//Programa que nos proporciono como ejemplo para la primer tarea

#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>
#include <limits.h>

int main(int argc, char *argv[]) {
    struct dirent *archivo;
        DIR *dir;
//      const dir_name;
        if (argc != 2) {
                printf("Indique el directorio a mostrar\n");
                return 1;
        }
        dir = opendir(argv[1]);
        while ((archivo = readdir(dir)) != 0) {
                printf("%s/%s\n",argv[1], archivo->d_name);

        }
}
