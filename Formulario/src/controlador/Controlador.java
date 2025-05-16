package controlador;

import vista.Vista;
import modelo.Modelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Controlador implements ActionListener {

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.Button_Enviar.addActionListener(this);
        this.vista.Button_Limpiar.addActionListener(this);
        this.vista.Button_Salir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.Button_Enviar) {
            enviar();
        } else if (e.getSource() == vista.Button_Limpiar) {
            limpiar();
        } else if (e.getSource() == vista.Button_Salir) {
            salir();
        }
    }

    public void enviar() {
        String nombre = vista.jText1_Nombre.getText();
        String apellido = vista.jText1_Apellido.getText();
        String dni = vista.jText1_Dni.getText();
        String pasaporte = vista.jText1_Pasaporte.getText();
        String telefono = vista.jText1_Telefono.getText();
        String codigoPostalTexto = vista.jText1_Codigo_Postal.getText();
        String domicilio = vista.jText1_Domicilio.getText();

        boolean nombreValido = modelo.NombreValido(nombre);
        boolean apellidoValido = modelo.AppelidoValido(apellido);
        boolean telValido = modelo.telefonoValido(telefono);
        boolean domicilioValido = modelo.domicilioValido(domicilio);
        boolean codigoPostalValido = modelo.codigoPostalValido(codigoPostalTexto);
        boolean documentoValido = modelo.validarDocumento(dni, pasaporte);

        if (nombreValido && apellidoValido && documentoValido
                && telValido && codigoPostalValido && domicilioValido) {
            JOptionPane.showMessageDialog(null, "Formulario válido. ¡Datos enviados!");
        } else {
            JOptionPane.showMessageDialog(null, "Hay errores en el formulario. Verifica los campos.");
        }

    }

    public void limpiar() {
        vista.jText1_Nombre.setText("");
        vista.jText1_Dni.setText("");
        vista.jText1_Pasaporte.setText("");
        vista.jText1_Telefono.setText("");
        vista.jText1_Codigo_Postal.setText("");
        vista.jText1_Domicilio.setText("");
        vista.jText1_Apellido.setText("");
    }

    public void salir() {
        System.out.println("Salio del formulario");
        System.exit(0);
    }

}
