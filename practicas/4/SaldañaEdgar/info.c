/* Este programa genera un txt y lo vuelve de solo lectura */

#include <stdio.h>
#include <sys/stat.h>

int main(int argc, char const *argv[])
{
	FILE *ptrTxt;

	if ((ptrTxt = fopen("datos.txt","w")) == NULL)
	{
		printf("%s\n","No fue posible crear el archivo txt");
	}

	fprintf(ptrTxt, "%s\n","Soy de solo lectura");
	chmod("datos.txt", 0444);

	fclose(ptrTxt);

	return 0;
}