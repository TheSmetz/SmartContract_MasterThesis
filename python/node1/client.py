import socket


class Client:
    def __init__(self, port, ip):
        self.port = port
        self.ip = ip

    def run_client(self, msg):
        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client.connect((self.ip, self.port))
        client.send(msg)
        from_server = client.recv(4096)
        client.close()
