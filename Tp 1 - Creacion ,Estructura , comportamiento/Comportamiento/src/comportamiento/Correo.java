
package comportamiento;

import java.util.ArrayList;
import java.util.List;


 class Correo {
    
     private List<Mensaje> mensajeros = new ArrayList<>();

   
     public void registrarMensaje(Mensaje mensajero){
         
         mensajeros.add(mensajero);
         
     }
  
     
     public void eliminarMensaje(Mensaje mensajero){
         
         mensajeros.remove(mensajero);
         
     }
     
     
     
     public void notificarMensajeros(){
         
         
         for(Mensaje mensajero : mensajeros){
             
             mensajero.actualizar();
             System.out.println("Se notifico con exito");
         }
         
     }
     
     
}
