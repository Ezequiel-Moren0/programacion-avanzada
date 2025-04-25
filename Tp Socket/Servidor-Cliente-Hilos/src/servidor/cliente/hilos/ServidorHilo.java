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
            out.writeUTF("Bienvenido " + nombre + ", escribe tu mensaje:");
            while (true) {
                String mensaje = in.readUTF();
                System.out.println("[" + nombre + "]: " + mensaje);

                if (mensaje.startsWith("RESOLVE")) {
                    String operacion = mensaje.substring(7).trim();
                    String resultado = resolverOperacion(operacion);
                    out.writeUTF("Resultado: " + resultado);
                } else if (mensaje.startsWith("*ALL")) {
                    enviarATodos("[" + nombre + "]: " + mensaje.substring(4).trim());
                } else if (mensaje.startsWith("*")) {
                    int espacio = mensaje.indexOf(" ");
                    if (espacio > 1) {
                        String destino = mensaje.substring(1, espacio);
                        String texto = mensaje.substring(espacio + 1);
                        enviarAPersona(destino, "[" + nombre + "]: " + texto);
                    }
                } else {
                    out.writeUTF("Formato no reconocido.");
                }
            }
        } catch (IOException e) {
            System.out.println(nombre + " se ha desconectado.");
        } finally {
            clientes.remove(this);
        }
    }

    private void enviarATodos(String mensaje) {
        for (ServidorHilo c : clientes) {
            try {
                c.out.writeUTF(mensaje);
            } catch (IOException e) {}
        }
    }

    private void enviarAPersona(String nombreDestino, String mensaje) {
        for (ServidorHilo c : clientes) {
            if (c.nombre.equalsIgnoreCase(nombreDestino)) {
                try {
                    c.out.writeUTF(mensaje);
                } catch (IOException e) {}
                return;
            }
        }
        try {
            out.writeUTF("No se encontró al cliente: " + nombreDestino);
        } catch (IOException e) {}
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
