import socket
import threading
import time
from client import Client

serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serv.bind(('0.0.0.0', 1111))
serv.listen(5)
init = False

def run_client(message):
    cln = Client(2222,'0.0.0.0')
    cln.run_client(message)
    time.sleep(2)

def run_server():
    
    while True:
        conn, addr = serv.accept()
        from_client = ''
        while True:
            data = conn.recv(4096)
            if not data: break
            from_client += data
            if 'RUN' in from_client:
                init = True
                x = threading.Thread(target=run_client, args=('INIT',))
                x.start()
            if 'INIT' in from_client:
                print from_client
            else: break
        conn.close()
        print 'client disconnected'

run_server()