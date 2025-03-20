
package poo;


public class Frutas {
    
    private String nombre;
    private double precio;

    public Frutas(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
    
    
      @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
    
    
}
