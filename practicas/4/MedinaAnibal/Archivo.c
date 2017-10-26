#include <stdio.h>
#include <stdlib.h>
 
int main(int argc, char** argv)
{
	FILE *fp;
	char cad;
	int i;
	fp = fopen ( "fichero.txt", "r" );        
	if (fp==NULL) {fputs ("error",stderr); exit (1);}
	while((cad=fgetc(fp))!=EOF){
        printf("%c",cad);
    }
	fclose ( fp );
	return 0;
}