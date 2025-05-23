package servidor.cliente.hilos;

import java.io.*;
import java.net.Socket;
import java.util.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
// import servidor.cliente.hilos.controlador.Controlador; // Esto ya no sería necesario en el ServidorHilo

public class ServidorHilo extends Thread {

    // private static List<String> clientes = new ArrayList<>(); // Esta lista se moverá al Servidor
    // private Controlador controlador; // Esto ya no sería necesario aquí si el controlador es de UI del cliente
    private DataInputStream in;
    private DataOutputStream out;
    private String nombre;
    private static int contadorClientes = 1;
    private final int id;
    private Socket sc;
    private Servidor servidorInstance; // Referencia a la instancia del Servidor

    public ServidorHilo(Socket sc, DataInputStream in, DataOutputStream out, String nombre, Servidor servidorInstance) {
        this.sc = sc;
        this.in = in;
        this.out = out;
        this.nombre = nombre;
        this.id = contadorClientes++;
        this.servidorInstance = servidorInstance; // Guardar la referencia al Servidor
        // No agregamos el nombre a una lista estática aquí, el Servidor lo gestiona.

        System.out.println("Nuevo cliente conectado: " + getNombreIdentificado());
    }

    // Getter para DataOutputStream, necesario para que el Servidor envíe mensajes
    public DataOutputStream getOut() {
        return out;
    }
    
    // Ya no necesitas setControlador si no vas a manipular la UI del cliente desde aquí.
    /*
    public void setControlador(Controlador c) {
        this.controlador = c;
    }
    */
    
    public String getNombreIdentificado() {
        return nombre + " (ID#" + id + ")";
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                String mensajeCliente = in.readUTF();
                System.out.println("Mensaje recibido de " + getNombreIdentificado() + ": " + mensajeCliente);

                // Lógica para procesar el mensaje
                // Por ejemplo, si es un mensaje de chat a otro cliente:
                if (mensajeCliente.startsWith("MSG_TO:")) {
                    String[] partes = mensajeCliente.substring("MSG_TO:".length()).split(":", 2);
                    if (partes.length == 2) {
                        String destinatario = partes[0].trim();
                        String mensaje = partes[1].trim();
                        servidorInstance.enviarMensajeACliente(destinatario, getNombreIdentificado(), mensaje);
                    }
                } else if (mensajeCliente.startsWith("CALC:")) {
                    String operacion = mensajeCliente.substring("CALC:".length());
                    String resultado = resolverOperacion(operacion);
                    out.writeUTF("CALC_RESULT:" + resultado); // Envía el resultado de la calculadora al cliente
                }
                // Aquí puedes añadir más tipos de mensajes o lógica de tu aplicación
            }
        } catch (IOException e) {
            System.out.println("Cliente " + getNombreIdentificado() + " se ha desconectado.");
            // Cuando un cliente se desconecta, se remueve de la lista global
            servidorInstance.removerCliente(getNombreIdentificado());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (sc != null) sc.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar streams/socket para " + getNombreIdentificado() + ": " + e.getMessage());
            }
        }
    }

    private String resolverOperacion(String op) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            Object result = engine.eval(op);
            return result.toString();
        } catch (Exception e) {
            return "Error en operación.";
        }
    }
}