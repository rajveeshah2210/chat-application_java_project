import java.net.*;
import java.io.*;

class server {
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    BufferedReader b;
    PrintWriter out;

    // Constructor of class server
    public server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to acceptconnection");
            System.out.println("waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("reader started...");

           try {
            while (!socket.isClosed()) {
                String msg = br.readLine();
                if (msg.equals("exit")) {
                    System.out.println("Client teminated the chat");
                    socket.close();
                    break;
                }
                System.out.println("client : " + msg);
            }
           } catch (Exception e) {
              // e.printStackTrace();
              System.out.println("Connection is closed");
           }
        };
        new Thread(r1).start();

    }

    public void startWriting() {

        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                while (!socket.isClosed()) {
                    b = new BufferedReader(new InputStreamReader(System.in));
                    String content = b.readLine();
                    
                    out.println(content);
                    out.flush();
                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Connection is closed");
                // e.printStackTrace();
            }

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is server...going to start server");
        new server();
    }
}