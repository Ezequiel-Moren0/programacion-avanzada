
package creacion;


public class Biblioteca {
    
    private static Biblioteca instancia;
    
    private String libros;
    
    private String revistas;

    
    
    public Biblioteca(String libros, String revistas) {
        this.libros = libros;
        this.revistas = revistas;
    }
    
    
    //Metodo estatico para obtener la instancia unica
    public static Biblioteca obtenerInstancia(String libros , String revistas){
        
        if(instancia == null){
            
            instancia = new Biblioteca(libros , revistas);
            
        }
        
        return instancia;
        
    }

    public void mostrarContenido(){
        
        System.out.println("Libros: "+libros);
        System.out.println("Revistas: "+revistas);
        
    }
    
    
    public String getLibros() {
        return libros;
    }

    public void setLibros(String libros) {
        this.libros = libros;
    }

    public String getRevistas() {
        return revistas;
    }
    
    public void setRevistas(String revistas) {
        this.revistas = revistas;
    }

   
    
}
