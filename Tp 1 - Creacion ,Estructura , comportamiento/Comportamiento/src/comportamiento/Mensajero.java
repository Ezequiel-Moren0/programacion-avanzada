
package comportamiento;


 class Mensajero implements Mensaje {
    
    private String nombre; 

    public Mensajero(String nombre) {
        this.nombre = nombre;
    }
    
    
    @Override
    public void actualizar(){
        
        System.out.println("El mensajero: "+nombre+" Envio el mensaje.");
        
        
    }
    
    
    
}
