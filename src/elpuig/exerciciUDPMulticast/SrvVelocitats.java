package elpuig.exerciciUDPMulticast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class SrvVelocitats {
    /* Servidor Multicast que proporciona la velocitat simulada d'un cos */

    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    Velocitat simulator;

    public SrvVelocitats(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        simulator = new Velocitat(100);
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = ByteBuffer.allocate(4).putInt(simulator.agafaVelocitat()).array();
            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }


        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        //Canvieu la X.X per un número per formar un IP.
        //Que no sigui la mateixa que la d'un altre company
        SrvVelocitats srvVel = new SrvVelocitats(5557, "224.0.10.10");
        srvVel.runServer();
        System.out.println("Parat!");

    }
}
