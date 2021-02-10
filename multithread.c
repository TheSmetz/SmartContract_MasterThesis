#include "client.h"

#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h>  //Header file for sleep(). man 3 sleep for details. 
#include <pthread.h> 

void *client(void *vargp){
    run_client();
}

int main(){
    
}
