import socket
import threading
import time
from client import Client

serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serv.bind(('0.0.0.0', 3333))
serv.listen(5)
init = False

def run_client(message):
    cln = Client(1111,'0.0.0.0')
    cln.run_client(message)

def run_server():
    global init
    while True:
        conn, addr = serv.accept()
        from_client = ''
        while True:
            data = conn.recv(4096)
            if not data: break
            from_client += data
            if 'INIT' in from_client and init==False:
                time.sleep(1)
                init = True
                x = threading.Thread(target=run_client, args=('INIT',))
                x.start()
            else: break
        conn.close()
        print 'client disconnected'

run_server()