package servidor.cliente.hilos;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try {
            Socket sc = new Socket("127.0.0.1", 5000);
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.println(in.readUTF()); // pedir nombre
            String nombre = scanner.nextLine();
            out.writeUTF(nombre);

            Thread recibir = new Thread(() -> {
                try {
                    while (true) {
                        System.out.println(">> " + in.readUTF());
                    }
                } catch (IOException e) {
                    System.out.println("Desconectado.");
                }
            });
            recibir.start();

            while (true) {
                String mensaje = scanner.nextLine();
                out.writeUTF(mensaje);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
