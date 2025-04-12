package servidor.cliente.hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServidorHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    private String apellidoCliente;

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente, String apellidoCliente) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
    }

    @Override
    public void run() {

    }

}
