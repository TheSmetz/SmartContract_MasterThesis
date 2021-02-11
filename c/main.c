#include "client.h"
#include "server.h"
#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h>  //Header file for sleep(). man 3 sleep for details. 
#include <pthread.h> 
extern char lastMessage[200];

void *client(void *vargp){
    run_client();
    return NULL;
}

void *server(void *vargp){
    int res = run_server();
    if(res==0){
        printf("%s\n",lastMessage);
    }
    return NULL;
}

int main(){
    pthread_t thread_id; 
    printf("Running server\n"); 
    pthread_create(&thread_id, NULL, server, NULL); 
    pthread_join(thread_id, NULL); 
    printf("After Thread\n");
    close_server();
    run_client();
}