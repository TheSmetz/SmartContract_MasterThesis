from server import Server

s2 = Server('i2',2222,'0.0.0.0')

s2.set_client(3333,'0.0.0.0')

s2.run_server()