package servidor.cliente.hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        try {
            ServerSocket servidor = new ServerSocket(5000);
            Socket sc;

            System.out.println("El servidor se a iniciado");
            System.out.println("-------------------------");

            while (true) {

                sc = servidor.accept(); // El servidor se queda parado hasta que encuentre un cliente

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Ingresa el nombre: ");
                String nombreCliente = in.readUTF();

                out.writeUTF("Ingrese el apellido: ");
                String apellidoCliente = in.readUTF();

                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente, apellidoCliente);
                hilo.start();

                System.out.println("Nueva conexión de cliente: " + nombreCliente + " " + apellidoCliente);

            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
