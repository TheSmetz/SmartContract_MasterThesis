from server import Server

s1 = Server('i1',1111,'0.0.0.0')

s1.set_client(2222,'0.0.0.0')

s1.run_server()