
package creacion;


public class Creacion {

    
    public static void main(String[] args) {
        
        Biblioteca biblioteca = Biblioteca.obtenerInstancia("Matematica", "Comida");
        
        biblioteca.mostrarContenido();
        
        System.out.println("-----------");
        biblioteca.setLibros("Lengua");
        biblioteca.setRevistas("Peluqueria");
        
        biblioteca.mostrarContenido();
        
    }
    
}
