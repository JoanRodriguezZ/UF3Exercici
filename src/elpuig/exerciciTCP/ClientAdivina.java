package elpuig.exerciciTCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientAdivina extends Thread {
    /* CLient TCP que ha endevinar un número pensat per SrvTcpAdivina.java */

    String hostname;
    int port;
    boolean continueConnected;
    Llista serverData = null;
    int nomLlista = 0;

    public ClientAdivina(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        continueConnected = true;
    }

    public void run() {

        Socket socket;
        ObjectInputStream  in;
        ObjectOutputStream out;

        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            in = new ObjectInputStream (socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            while(continueConnected){
                Llista llista = getRequest(serverData);
                out.writeObject(llista);
                out.flush();
                serverData = (Llista) in.readObject();

            }
            close(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Llista getRequest(Llista serverData) {
        if (serverData != null){
            System.out.println("Llista " + serverData.getNom() + " rebuda:");
            for(Integer i: serverData.getNumberList()){
                System.out.println(i);
            }
        }

        nomLlista++;
        return new Llista(String.valueOf(nomLlista));
    }

    public boolean mustFinish(String dades) {
        if (dades.equals("exit")) return false;
        return true;

    }

    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClientAdivina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
		/*if (args.length != 2) {
            System.err.println(
                "Usage: java ClientTcpAdivina <host name> <port number>");
            System.exit(1);
        }*/

        // String hostName = args[0];
        // int portNumber = Integer.parseInt(args[1]);
        ClientAdivina client = new ClientAdivina("localhost",5558);
        client.start();
    }

}
