package servidor.cliente.hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("\n");

            Socket sc = new Socket("127.0.0.1", 5000);

            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());

            String mensaje = in.readUTF();                    
            System.out.println(mensaje);                    
            String nombre = scanner.next();
            out.writeUTF(nombre);                             

            mensaje = in.readUTF();                           
            System.out.println(mensaje);
            String apellido = scanner.next();
            out.writeUTF(apellido);

            ClienteHilo hilo = new ClienteHilo(in, out);

            hilo.start();
            hilo.join();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
