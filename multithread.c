#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h>  //Header file for sleep(). man 3 sleep for details. 
#include <pthread.h> 

void *client(void *vargp){
    run_client();
    return NULL;
}

int main(){
    pthread_t thread_id; 
    printf("Running client\n"); 
    pthread_create(&thread_id, NULL, client, NULL); 
    pthread_join(thread_id, NULL); 
    printf("After Thread\n");
}
