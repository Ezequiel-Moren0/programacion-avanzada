package servidor.cliente.hilos;

import java.io.*;
import java.util.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class ServidorHilo extends Thread {

    private static List<ServidorHilo> clientes = new ArrayList<>();
    private DataInputStream in;
    private DataOutputStream out;
    private String nombre;
    private static int contadorClientes = 1;
    private final int id;

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombre) {
        this.in = in;
        this.out = out;
        this.nombre = nombre;
        this.id = contadorClientes++;
        clientes.add(this);

        System.out.println("Nuevo cliente conectado: " + getNombreIdentificado());
        System.out.println("Clientes actualmente conectados:");
        for (ServidorHilo c : clientes) {
            System.out.println("- " + c.getNombreIdentificado());
        }
        System.out.println("Total: " + clientes.size() + " cliente(s)\n");
    }

    public String getNombreIdentificado() {
        return nombre + " (ID#" + id + ")";
    }

    @Override
    public void run() {
        try {
            out.writeUTF("Bienvenido " + getNombreIdentificado() + "!");

            while (true) {
                mostrarMenu();
                String opcion = in.readUTF();

                switch (opcion) {
                    case "1":
                        out.writeUTF("Escribe la operación matemática (ej: 45*23/54+234):");
                        String operacion = in.readUTF();
                        String resultado = resolverOperacion(operacion);
                        out.writeUTF("Resultado: " + resultado);
                        break;

                    case "2":
                        mostrarSubMenuMensajes();
                        String subopcion = in.readUTF();

                        if (subopcion.equalsIgnoreCase("ALL")) {
                            out.writeUTF("Escribe el mensaje para TODOS:");
                            String mensajeAll = in.readUTF();
                            enviarATodos("[" + getNombreIdentificado() + "]: " + mensajeAll);
                        } else {
                            out.writeUTF("Escribe el mensaje para los destinatarios (" + subopcion + "):");
                            String mensaje = in.readUTF();
                            enviarAMultiples(subopcion, "[" + getNombreIdentificado() + "]: " + mensaje);
                        }
                        break;

                    case "3":
                        out.writeUTF("Saliendo del chat. ¡Hasta luego!");
                        break;

                    default:
                        out.writeUTF("Opción inválida.");
                        continue;
                }

                if (opcion.equals("3")) break;
            }

        } catch (IOException e) {
            System.out.println(getNombreIdentificado() + " se ha desconectado.");
        } finally {
            clientes.remove(this);
            try { in.close(); } catch (IOException e) {}
            try { out.close(); } catch (IOException e) {}
            enviarATodos("El cliente " + getNombreIdentificado() + " se ha desconectado.");

            System.out.println("Cliente desconectado: " + getNombreIdentificado());
            System.out.println("Clientes actualmente conectados:");
            for (ServidorHilo c : clientes) {
                System.out.println("- " + c.getNombreIdentificado());
            }
            System.out.println("Total: " + clientes.size() + " cliente(s)\n");
        }
    }

    private void mostrarMenu() throws IOException {
        out.writeUTF("\n--- MENÚ ---\n" +
                     "1: Resolver operación matemática\n" +
                     "2: Enviar mensaje\n" +
                     "3: Salir\n" +
                     "Seleccione una opción:");
    }

    private void mostrarSubMenuMensajes() throws IOException {
        out.writeUTF("Clientes conectados:");
        for (ServidorHilo c : clientes) {
            out.writeUTF("- " + c.getNombreIdentificado());
        }
        out.writeUTF("Escribe el/los nombre(s) del destinatario separado(s) por coma (ej: Lucas,Ezequiel) o escribe 'ALL':");
    }

    private void enviarATodos(String mensaje) {
        System.out.println("Mensaje a todos: " + mensaje);
        for (ServidorHilo c : clientes) {
            try {
                c.out.writeUTF(mensaje);
            } catch (IOException e) {}
        }
    }

    private void enviarAMultiples(String nombres, String mensaje) {
        String[] destinatarios = nombres.split(",");
        Set<String> noEncontrados = new HashSet<>();

        for (String nom : destinatarios) {
            boolean encontrado = false;
            for (ServidorHilo c : clientes) {
                if (c.nombre.equalsIgnoreCase(nom.trim()) || c.getNombreIdentificado().equalsIgnoreCase(nom.trim())) {
                    try {
                        c.out.writeUTF(mensaje);
                        System.out.println("Mensaje de " + getNombreIdentificado() + " a " + nom + ": " + mensaje);
                        encontrado = true;
                    } catch (IOException e) {}
                }
            }
            if (!encontrado) {
                noEncontrados.add(nom.trim());
            }
        }

        if (!noEncontrados.isEmpty()) {
            try {
                out.writeUTF("No se encontraron los siguientes destinatarios: " + String.join(", ", noEncontrados));
            } catch (IOException e) {}
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
