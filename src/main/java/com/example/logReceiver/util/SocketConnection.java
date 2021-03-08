package com.example.logReceiver.util;
import com.example.logReceiver.proto.LogProto;

import java.net.*;
import java.io.*;

/**
 * Socket connection
 */
public class SocketConnection {
    private static final String HOST = "localhost";
    private static final int PORT_CLIENT = 9999;
    private static final int PORT_SERVER = 9998;

    /**
     * Create socket connection (client) for data sending
     * @param report Report to be sent through socket
     * @return String Message received from server socket
     */
    public static String runClient(LogProto.Report report) {

        try {
            Socket sc = new Socket(HOST, PORT_CLIENT);
            System.out.println("Connecting to:" + HOST + " port:" + PORT_CLIENT);

            OutputStream output = sc.getOutputStream();
            report.writeTo(output);
            output.flush();
            sc.close();
            System.out.println("Data sent to socket from Java");

            return runServer();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
            return ex.getMessage();
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            return ex.getMessage();
        }
    }

    /**
     * Create socket connection (server) to receive, read and print messages
     * @return String Final received message from client socket
     * @throws IOException I/O error
     */
    public static String runServer() throws IOException {
        try {
            ServerSocket socket = new ServerSocket(PORT_SERVER);
            Socket ss = socket.accept();

            InputStream input = ss.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();

            if (line != null){
                System.out.println(line);
            }
            socket.close();
            ss.close();
            return line;
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
            return ex.getMessage();
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            return ex.getMessage();
        }

    }
}