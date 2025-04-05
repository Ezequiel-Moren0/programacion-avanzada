
package comportamiento;


public class Comportamiento {

    
    public static void main(String[] args) {
      
        Correo correo = new Correo();
        
        
        Mensaje mensajero1 = new Mensajero("Mensajero 1");
        Mensaje mensajero2 = new Mensajero("Mensajero 2");
        
        correo.registrarMensaje(mensajero1);
        correo.registrarMensaje(mensajero2);
       
        correo.notificarMensajeros();
        
        
       
        
    }
    
}
