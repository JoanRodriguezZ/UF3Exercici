package elpuig.exerciciTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SrvAdivina {
    /* Servidor TCP que genera un número perquè ClientTcpAdivina.java jugui a encertar-lo
     * i on la comunicació dels diferents jugador passa per el Thread : ThreadServidorAdivina.java
     * */

    int port;
    Llista llista;

    public SrvAdivina(int port) {
        this.port = port;
//        llista = new Llista("nam", ));
    }

    public void listen() {
        ServerSocket serverSocket;
        Socket clientSocket = null;


        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                //espera connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
//                ThreadSrvAdivina FilServidor = new ThreadSrvAdivina(clientSocket, llista);
                ThreadSrvAdivina FilServidor = new ThreadSrvAdivina(clientSocket);

                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(SrvAdivina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
		/*if (args.length != 1) {
            System.err.println("Usage: java SrvTcpAdivina <port number>");
            System.exit(1);
        }*/


        //int port = Integer.parseInt(args[0]);
        SrvAdivina srv = new SrvAdivina(5558);
        srv.listen();

    }
}
