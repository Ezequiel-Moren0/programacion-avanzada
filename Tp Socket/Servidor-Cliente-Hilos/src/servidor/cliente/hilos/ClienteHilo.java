package servidor.cliente.hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClienteHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

   @Override
public void run() {
    try {
        while (true) {
            String mensaje = in.readUTF();
            System.out.println("\n" + mensaje);
        }
    } catch (IOException e) {
        System.out.println("Conexión cerrada por el servidor.");
    }
}
}
