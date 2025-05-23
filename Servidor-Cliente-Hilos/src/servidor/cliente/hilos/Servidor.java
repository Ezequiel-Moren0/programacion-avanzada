package servidor.cliente.hilos;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Servidor {
    private static Map<String, ServidorHilo> clientesConectadosEnServidor = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        Servidor servidor1 = new Servidor();
        servidor1.iniciarServidor();

    }
        public void iniciarServidor(){
            Operaciones h1 = new Operaciones();
        try {
            ServerSocket servidorSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado...");

            while (true) {
                Socket sc = servidorSocket.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                String nombre = in.readUTF(); // El cliente envía su nombre al conectarse
                
                // Crear el ServidorHilo
                ServidorHilo hilo = new ServidorHilo(sc, in, out, nombre, Servidor.this);
                
                System.out.println("Nuevo cliente: " + nombre);
                String nombreFinal = hilo.getNombreIdentificado();
                out.writeUTF(nombreFinal);

                // Agregar el hilo a la lista de clientes conectados en el servidor
                clientesConectadosEnServidor.put(nombreFinal, hilo);
                
                hilo.start(); // Iniciar el hilo del cliente

                // Notificar a todos los clientes la lista actualizada
                enviarListaClientesATodos();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            }
        }
        

    // Método para obtener la lista actual de nombres de clientes
    public List<String> getNombresClientesConectados() {
        return new ArrayList<>(clientesConectadosEnServidor.keySet());
    }

    // Método para eliminar un cliente cuando se desconecta
    public void removerCliente(String nombreCliente) {
        clientesConectadosEnServidor.remove(nombreCliente);
        System.out.println("Cliente desconectado: " + nombreCliente);
        System.out.println("Clientes actualmente conectados: " + clientesConectadosEnServidor.size());
        // Notificar a todos los clientes sobre la lista actualizada
        enviarListaClientesATodos();
    }

    // Método para enviar un mensaje a un cliente específico
    public void enviarMensajeACliente(String destinatarioNombre, String remitenteNombre, String mensaje) {
        ServidorHilo destinatarioHilo = clientesConectadosEnServidor.get(destinatarioNombre);
        if (destinatarioHilo != null) {
            try {
                destinatarioHilo.getOut().writeUTF("Mensaje de " + remitenteNombre + ": " + mensaje);
            } catch (IOException e) {
                System.err.println("Error al enviar mensaje a " + destinatarioNombre + ": " + e.getMessage());
            }
        } else {
            System.out.println("Destinatario " + destinatarioNombre + " no encontrado.");
            // Opcional: Notificar al remitente que el destinatario no está conectado
            // Puedes agregar un parámetro DataOutputStream al método para el remitente
            // y enviarles un mensaje de error.
        }
    }

    // Método para enviar la lista actualizada de clientes a todos los clientes conectados
    public void enviarListaClientesATodos() {
        List<String> nombresClientes = getNombresClientesConectados();
        // Construye un mensaje especial para la lista de clientes
        String listaClientesMsg = "CLIENT_LIST:" + String.join(",", nombresClientes);
        
        for (ServidorHilo sh : clientesConectadosEnServidor.values()) {
            try {
                sh.getOut().writeUTF(listaClientesMsg);
            } catch (IOException e) {
                System.err.println("Error al enviar lista a " + sh.getNombreIdentificado() + ": " + e.getMessage());
            }
        }
        System.out.println("Lista de clientes actualizada enviada: " + nombresClientes);
    }
    
    
}