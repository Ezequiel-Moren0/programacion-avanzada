package servidor.cliente.hilos;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor iniciado...");

            while (true) {
                Socket sc = servidor.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Escribe tu nombre:");
                String nombre = in.readUTF();

                System.out.println("Nuevo cliente: " + nombre);

                ServidorHilo hilo = new ServidorHilo(in, out, nombre);
                hilo.start();
            }

        } catch (IOException e) {
        }
    }
}
