package formulario;

import vista.Vista;
import controlador.Controlador;
import modelo.Modelo;

public class Formulario {

    public static void main(String[] args) {

        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo, vista);
        vista.setVisible(true);
        
    }

}
