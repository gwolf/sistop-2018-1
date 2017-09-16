#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>

int main(int argc, char *argv[]) {
    struct dirent *archivo;
	DIR *dir;
	if (argc != 2) {
		printf("Indique el directorio a mostrar\n");
		return 1;
	}
	dir = opendir(argv[1]);
	while ((archivo = readdir(dir)) != 0) {
		printf("%s\t", archivo->d_name);
	}
	printf("\n");
	closedir(dir);
}
