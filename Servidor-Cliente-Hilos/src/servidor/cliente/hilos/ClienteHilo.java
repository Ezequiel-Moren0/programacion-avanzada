package servidor.cliente.hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import servidor.cliente.hilos.controlador.Controlador; // Asegúrate de que esta importación sea correcta

public class ClienteHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;
    private Controlador controladorCliente; // Referencia al controlador del cliente

    public ClienteHilo(DataInputStream in, DataOutputStream out, Controlador controladorCliente) {
        this.in = in;
        this.out = out;
        this.controladorCliente = controladorCliente;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String mensaje = in.readUTF();
                System.out.println("\nMensaje recibido del servidor: " + mensaje);

                // Si el mensaje es una lista de clientes
                if (mensaje.startsWith("CLIENT_LIST:")) {
                    String clientesStr = mensaje.substring("CLIENT_LIST:".length());
                    String[] nombres = clientesStr.split(",");
                    // Pasar la lista de nombres al controlador del cliente
                    controladorCliente.actualizarUsuariosConectados(java.util.Arrays.asList(nombres));
                } 
                // Si es un resultado de calculadora
                else if (mensaje.startsWith("CALC_RESULT:")) {
                    String resultado = mensaje.substring("CALC_RESULT:".length());
                    // Aquí el controlador debería tener un método para mostrar esto en la calculadora
                    controladorCliente.mostrarResultadoCalculadora(resultado);
                }
                // Si es un mensaje directo o de chat
                else {
                    // Otros mensajes (e.g., mensajes de chat de otros usuarios)
                    controladorCliente.mostrarMensajeEnChat(mensaje); // Método en el controlador para mostrar en el chat
                }
            }
        } catch (IOException e) {
            System.out.println("Conexión cerrada por el servidor o error de lectura.");
            // Opcional: Notificar al controlador que la conexión se ha perdido
            controladorCliente.manejarDesconexionServidor();
        }
    }
}