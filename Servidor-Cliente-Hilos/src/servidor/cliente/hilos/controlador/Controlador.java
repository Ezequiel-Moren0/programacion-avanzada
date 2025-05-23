package servidor.cliente.hilos.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import servidor.cliente.hilos.Operaciones;
import servidor.cliente.hilos.vista.Bienvenida;
import servidor.cliente.hilos.vista.Calculadora;
import servidor.cliente.hilos.vista.Mensajeria;
import servidor.cliente.hilos.vista.Menu;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import servidor.cliente.hilos.ServidorHilo; 
import servidor.cliente.hilos.ClienteHilo;
import servidor.cliente.hilos.Servidor;

public class Controlador implements ActionListener {
    private final Operaciones OP;
    private Bienvenida bienvenida;
    private Menu menu;
    private Calculadora calculadora;
    private Mensajeria mensajeria;
    
    private Socket socketCliente; 
    private DataOutputStream out;
    private DataInputStream in;
    private ClienteHilo clienteHilo; 
    
   
    private final Map<String, String> clientesConectadosUI = new HashMap<>(); 
    private String nombreUsuarioCliente; 

    public Controlador(Operaciones op, Bienvenida bienv, Menu menu1, Calculadora calc, Mensajeria mensaje) {
        this.OP = op;
        this.bienvenida = bienv;
        this.menu = menu1;
        this.calculadora = calc;
        this.mensajeria = mensaje;

        // Añadir ActionListeners a los botones de las vistas
        this.bienvenida.conectar1.addActionListener(this);
        this.menu.calc_matematico.addActionListener(this);
        this.menu.mensajeria.addActionListener(this);
        this.menu.SALIR.addActionListener(this);
        this.calculadora.volver_menu.addActionListener(this);
        this.calculadora.resolve.addActionListener(this);
        this.mensajeria.volver_menu_msg.addActionListener(this);
        this.mensajeria.ENVIAR.addActionListener(this); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Botones de Bienvenida
        if (e.getSource() == bienvenida.conectar1) {
            nombreUsuarioCliente = bienvenida.ingreso1.getText();
            if (nombreUsuarioCliente.isEmpty()) {
                JOptionPane.showMessageDialog(bienvenida, "Por favor, ingresa un nombre de usuario.");
                return;
            }
            try {
                socketCliente = new Socket("localhost", 5000);
                in = new DataInputStream(socketCliente.getInputStream());
                out = new DataOutputStream(socketCliente.getOutputStream());
                
                out.writeUTF(nombreUsuarioCliente); 
                String nombreFinalServidor = in.readUTF(); 
                mensajeria.nombre_usuario.setText(nombreFinalServidor); 
                menu.user_name.setText(nombreFinalServidor);
                
                clienteHilo = new ClienteHilo(in, out, this); 
                clienteHilo.start();

                bienvenida.setVisible(false);
                menu.setVisible(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(bienvenida, "No se pudo conectar al servidor: " + ex.getMessage());
                ex.printStackTrace();
            }
        } 
       
        else if (e.getSource() == menu.calc_matematico) {
            menu.setVisible(false);
            calculadora.setVisible(true);
        } else if (e.getSource() == menu.mensajeria) {
            menu.setVisible(false);
            mensajeria.setVisible(true);
        } else if (e.getSource() == menu.SALIR) {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socketCliente != null) socketCliente.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            menu.dispose();
            System.exit(0); // Cerrar la aplicación del cliente
        }
        
        // Botones de la calculadora
        else if (e.getSource() == calculadora.volver_menu) {
            calculadora.setVisible(false);
            menu.setVisible(true);
        } else if (e.getSource() == calculadora.resolve) {
            try {
                out.writeUTF("CALC:" + calculadora.calculo.getText()); // Enviar operación al servidor
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(calculadora, "Error al enviar cálculo: " + ex.getMessage());
            }
        }
            
        // Botones de mensajería
        else if (e.getSource() == mensajeria.volver_menu_msg) {
            mensajeria.setVisible(false);
            menu.setVisible(true);
        } else if (e.getSource() == mensajeria.ENVIAR) {
            // Lógica para enviar mensaje a un destinatario específico (el que está seleccionado)
            String mensajeTxt = mensajeria.getMensajeToSend(); 
            String destinatario = mensajeria.getSelectedUser();
            if (destinatario != null && !mensajeTxt.isEmpty()) {
                try {
                    out.writeUTF("MSG_TO:" + destinatario + ":" + mensajeTxt);
                    mensajeria.appendMessage("Yo a " + destinatario + ": " + mensajeTxt); // Mostrar mi propio mensaje en el chat
                    mensajeria.clearMessageField(); // Limpiar el campo de texto
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mensajeria, "Error al enviar mensaje: " + ex.getMessage());
                }
            } else if (destinatario == null) {
                JOptionPane.showMessageDialog(mensajeria, "Por favor, selecciona un destinatario.");
            } else if (mensajeTxt.isEmpty()){
                 JOptionPane.showMessageDialog(mensajeria, "No puedes enviar un mensaje vacío.");
            }
        } else if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            String username = clickedButton.getActionCommand();
            mensajeria.setSelectedUser(username);
            mensajeria.mensaje_destinatario.setText(username);
            
          
        }
    }
    
    // Método llamado por ClienteHilo para actualizar la lista de usuarios
    public void actualizarUsuariosConectados(List<String> nombresUsuarios) {
        // Ejecutar en el hilo de despacho de eventos de Swing para evitar problemas de concurrencia
        javax.swing.SwingUtilities.invokeLater(() -> {
            mensajeria.Lista_Users.removeAll(); // limpiamos los botones viejos
            clientesConectadosUI.clear(); // Limpiar la lista interna para reconstruirla
            
            for (String nombreUsuario : nombresUsuarios) {
                if (!nombreUsuario.equals(this.mensajeria.nombre_usuario.getText())) { // No mostrarse a sí mismo
                    mensajeria.agregarUsuario(nombreUsuario, this);  // usamos `this` como ActionListener
                    clientesConectadosUI.put(nombreUsuario, nombreUsuario); // Guardar en el mapa local
                }
            }
            mensajeria.Lista_Users.revalidate();
            mensajeria.Lista_Users.repaint();
        });
    }

    // Método para que el ClienteHilo muestre resultados de calculadora
    public void mostrarResultadoCalculadora(String resultado) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            calculadora.valor_resultado.setText(resultado);
        });
    }

    // Método para que el ClienteHilo muestre mensajes de chat
    public void mostrarMensajeEnChat(String mensaje) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            mensajeria.appendMessage(mensaje);
        });
    }

    // Método para manejar la desconexión del servidor
    public void manejarDesconexionServidor() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Conexión con el servidor perdida.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            // Puedes redirigir al usuario a la pantalla de bienvenida o cerrar la aplicación
            bienvenida.setVisible(true);
            menu.setVisible(false);
            calculadora.setVisible(false);
            mensajeria.setVisible(false);
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socketCliente != null) socketCliente.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}