#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>

int main() {
    struct sockaddr_in server;
    int socket_desc;

    socket_desc = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (socket_desc == -1) {
        printf("Socket Creation Unsccessful");
    }

    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    server.sin_family = AF_INET;
    server.sin_port = htons(80);

    if (connect(socket_desc, (struct sockaddr *)&server, sizeof(server)) < 0) {
		puts("connect error");
		return 1;
	}
	
	puts("Connected");
	return 0;
}