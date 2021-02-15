from client import Client
from message import Message

cln = Client(1111,'0.0.0.0')
cln.run_client()
m = Message()
m.message_type = 'INIT'
cln.send_to_client(m)
cln.close()