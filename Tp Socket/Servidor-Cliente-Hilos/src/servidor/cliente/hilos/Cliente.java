package servidor.cliente.hilos;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;
     
        try (Socket socket = new Socket(host, puerto);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            out.writeUTF(nombre);  // Se espera que el servidor reciba este nombre

            ClienteHilo hiloRecepcion = new ClienteHilo(in, out);
            hiloRecepcion.start();

            // Bucle para enviar datos al servidor
            while (true) {
                String entrada = scanner.nextLine();
                out.writeUTF(entrada);

                if (entrada.equals("3")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
