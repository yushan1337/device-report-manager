package devicereport.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleSocketClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 18080;

        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println("hello from client");

            String response = reader.readLine();
            System.out.println("Response: " + response);
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
