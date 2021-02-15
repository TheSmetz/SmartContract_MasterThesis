from server import Server

s3 = Server('i3',3333,'0.0.0.0')

s3.set_client(1111,'0.0.0.0')

s3.run_server()