package servidor.cliente.hilos;


import java.util.Scanner;
import servidor.cliente.hilos.controlador.Controlador;
import servidor.cliente.hilos.vista.Bienvenida;
import servidor.cliente.hilos.vista.Calculadora;
import servidor.cliente.hilos.vista.Mensajeria;
import servidor.cliente.hilos.vista.Menu;

public class Cliente {

    public static void main(String[] args) {
            Operaciones h1=new Operaciones();
            Bienvenida bienvenida = new Bienvenida();
            Mensajeria mensajeria = new Mensajeria();
            Menu menu = new Menu();
            Calculadora calculadora = new Calculadora();

            // Pasa null para in y out inicialmente, el controlador los establecerá al conectar.
            Controlador c1 = new Controlador(h1, bienvenida, menu, calculadora, mensajeria);
            bienvenida.setVisible(true);
    }
}