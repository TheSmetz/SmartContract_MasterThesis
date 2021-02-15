import socket, pickle
from client import Client
from message import Message

class Server:
    def __init__(self, name, port, ip):
        self.name = name
        self.port = port
        self.ip = ip

    def set_client(self, port, ip):
        self.clnt = Client(port,ip)

    def run_server(self):
        serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        serv.bind((self.ip, self.port))
        serv.listen(5)
        while True:
            conn, addr = serv.accept()
            from_client = ''
            while True:
                data = conn.recv(4096)
                if not data: break
                from_client += data
                var = pickle.loads(from_client)
                self.validate_message(var)
            conn.close()

    def validate_message(self,msg):
        print msg.data
        if 'INIT' in msg.message_type:
            print 'Starting'
            poc = self.proof_of_computation()
            self.clnt.run_client()
            print 'Sending POC to next node'
            self.clnt.send_to_client(poc)
        elif 'PROOF' in msg.message_type:
            print 'Received POC of ', msg.author
            if msg.author != self.name:
                msg.data+=':'
                msg.data+=self.name
                self.clnt.run_client()
                print 'Sending POC to next node'
                self.clnt.send_to_client(msg)
            else:
                print 'calcolo check'
        else:
            'errore'

    def proof_of_computation(self):
        print 'Calculating proof of computation..'
        msg = Message()
        msg.author = self.name
        msg.message_type = 'PROOF'
        msg.data = 'blabla'
        return msg