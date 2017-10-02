#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> //forkis()

int32_t main(int32_t argc, char **argv){
  pid_t pid= fork(); 
  printf("\nRetorno de fork() %d\n",pid);
  if(pid){
    printf("Yo soy el padre %d\n",getpid());
  }else{
    printf("Yo soy el hijo %d\n",getpid());
  }

  return EXIT_SUCCESS;
}
