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

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombre) {
        this.in = in;
        this.out = out;
        this.nombre = nombre;
        clientes.add(this);
    }

    @Override
public void run() {
    try {
        out.writeUTF("Bienvenido " + nombre + "!");

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
                        enviarATodos("[" + nombre + "]: " + mensajeAll);
                    } else {
                        String nombreDestino = subopcion;
                        out.writeUTF("Escribe el mensaje para " + nombreDestino + ":");
                        String mensajePrivado = in.readUTF();
                        enviarAPersona(nombreDestino, "[" + nombre + "]: " + mensajePrivado);
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
        System.out.println(nombre + " se ha desconectado.");
    } finally {
        clientes.remove(this);
        try { in.close(); } catch (IOException e) {}
        try { out.close(); } catch (IOException e) {}
        enviarATodos("El cliente " + nombre + " se ha desconectado.");
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
        out.writeUTF("- " + c.nombre);
    }
    out.writeUTF("Escribe el nombre del destinatario o escribe 'ALL' para todos:");
}

    private void enviarATodos(String mensaje) {
    System.out.println("Mensaje a todos: " + mensaje); // 👈 aquí
    for (ServidorHilo c : clientes) {
        try {
            c.out.writeUTF(mensaje);
        } catch (IOException e) {}
    }
}

private void enviarAPersona(String nombreDestino, String mensaje) {
    boolean encontrado = false;
    for (ServidorHilo c : clientes) {
        if (c.nombre.equalsIgnoreCase(nombreDestino)) {
            try {
                c.out.writeUTF(mensaje);
                System.out.println("Mensaje privado de " + nombre + " a " + nombreDestino + ": " + mensaje); // 👈 aquí
                encontrado = true;
            } catch (IOException e) {}
            return;
        }
    }
    if (!encontrado) {
        try {
            out.writeUTF("No se encontró al cliente: " + nombreDestino);
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
