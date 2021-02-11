#include "socket.h"
#include "server.h"
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

char lastMessage[200] = {0};
struct sockaddr_in server, client;
int socket_desc, sock, clientLen, read_size;

int BindCreatedSocket(int hSocket)
{
    int iRetval = -1;
    int ClientPort = 5555;
    struct sockaddr_in remote = {0};
    /* Internet address family */
    remote.sin_family = AF_INET;
    /* Any incoming interface */
    remote.sin_addr.s_addr = htonl(INADDR_ANY);
    remote.sin_port = htons(ClientPort); /* Local port */
    iRetval = bind(hSocket, (struct sockaddr *)&remote, sizeof(remote));
    return iRetval;
}

int run_server()
{
    
    char client_message[200] = {0};
    char message[100] = {0};
    const char *pMessage = "hello aticleworld.com";
    //Create socket
    socket_desc = SocketCreate();
    if (socket_desc == -1)
    {
        printf("Could not create socket");
        return 1;
    }
    printf("Socket created\n");
    //Bind
    if (BindCreatedSocket(socket_desc) < 0)
    {
        //print the error message
        perror("bind failed.");
        return 1;
    }
    printf("bind done\n");
    //Listen
    listen(socket_desc, 3);
    //Accept and incoming connection
    printf("Waiting for incoming connections...\n");
    clientLen = sizeof(struct sockaddr_in);
    //accept connection from an incoming client
    sock = accept(socket_desc, (struct sockaddr *)&client, (socklen_t *)&clientLen);
    if (sock < 0)
    {
        perror("accept failed");
        return 1;
    }
    printf("Connection accepted.\n");
    printf("Waiting for messages . . .\n");

    while(1){
        memset(client_message, '\0', sizeof client_message);
        memset(message, '\0', sizeof message);
        //Receive a reply from the client
        if (recv(sock, client_message, 200, 0) < 0)
        {
            printf("recv failed");
            break;
        }

        //return client_message;
        strncpy(lastMessage, client_message, 200);

        return 0;
    }
    return 0;
}

void close_server() {
    close(socket_desc);
}
