
package creacion;


public class Creacion {

    
    public static void main(String[] args) {
        
        Biblioteca biblioteca = Biblioteca.obtenerInstancia("Matematica", "Comida");
        
        System.out.println("Libros: "+biblioteca.getLibros());
        System.out.println("Revistas: "+biblioteca.getRevistas());
        
        
        biblioteca.setLibros("Lengua");
        biblioteca.setRevistas("Peluqueria");
        
        System.out.println("------------------");
        System.out.println("Libros: "+biblioteca.getLibros());
        System.out.println("Revistas: "+biblioteca.getRevistas());
        
    }
    
}
