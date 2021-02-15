import socket, pickle


class Client:
    def __init__(self, port, ip):
        self.port = port
        self.ip = ip

    def run_client(self):
        self.client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client.connect((self.ip, self.port))

    def send_to_client(self, msg):
        msg_byte = pickle.dumps(msg)
        self.client.send(msg_byte)

    def close(self):
        self.client.close()