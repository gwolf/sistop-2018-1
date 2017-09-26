#include <stdio.h>


int main(){
   FILE *archivo;
   int caracter;
	
   archivo = fopen("archivo_prueba.txt","r");
	
   if (archivo == NULL){
      printf("\nError de apertura del archivo. \n\n");
   }
   else{
      printf("\nEl contenido del archivo de prueba es: \n\n");
   while((caracter = fgetc(archivo)) != EOF){
      printf("%c",caracter);
      }
   }
   fclose(archivo);
   return 0;
}
