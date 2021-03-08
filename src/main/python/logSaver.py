import socket
import time

from proto import logSaver_pb2
import datetime

HOST = "localhost"
PORT_SERVER = 9999
PORT_CLIENT = 9998
FILE = "./src/main/resources/logReport.txt"


def save_log(data_received):
    """
    Receive and parse the received binary data, then save it into a .txt file.
    :param data_received: binary data.
    """
    file = open(FILE, "a")

    try:
        report = logSaver_pb2.Report()

        report.ParseFromString(data_received)

        for log in report.logs:
            date = datetime.datetime.fromtimestamp(log.tstamp / 1e3)
            file.write("Timestamp:" + date.strftime('%Y-%m-%d %H:%M:%S') + "\t")
            file.write("UserId:" + str(log.userId) + "\t")
            file.write("Event:" + log.event + "\n")
        print("Data saved in file: ", FILE)
    finally:
        file.close()


def connect_socket_server():
    """Create socket connection (server) to receive data."""
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        s.bind((HOST, PORT_SERVER))
    except socket.error as err:
        print('Bind failed. Error Code : '.format(err))

    s.listen(10)
    conn, addr = s.accept()

    try:
        # Receiving data
        data = conn.recv(1024)
        if data:
            print('Data received {!r}'.format(data))
            save_log(data)
        else:
            print('No data from ', HOST)
    finally:
        s.close()


def connect_socket_client():
    """Create client socket."""
    time.sleep(1)
    s2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s2.connect((HOST, PORT_CLIENT))
    s2.send("Finishing connection")
    s2.close()


if __name__ == "__main__":
    connect_socket_server()
    connect_socket_client()
