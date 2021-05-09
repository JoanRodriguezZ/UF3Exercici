package elpuig.exerciciTCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadSrvAdivina implements Runnable {
    /* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */

    Socket clientSocket = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    Llista llista;
    boolean acabat;

//    public ThreadSrvAdivina(Socket clientSocket, Llista llista) throws IOException {
    public ThreadSrvAdivina(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
//        this.llista = llista;
        acabat = false;
        in = new ObjectInputStream(clientSocket.getInputStream());
        out= new ObjectOutputStream(clientSocket.getOutputStream());

    }

    @Override
    public void run() {
        while(!acabat) {

            try {
                llista = (Llista) in.readObject();
                llista = generaResposta(llista);
                out.writeObject(llista);
                out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Llista generaResposta(Llista llista) {
        List<Integer> llistaDesordenada = llista.getNumberList();
        List<Integer> llistaOrdenada = llistaDesordenada.stream().sorted().distinct().collect(Collectors.toList());
        llista.setNumberList(llistaOrdenada);
        return llista;
    }
}
