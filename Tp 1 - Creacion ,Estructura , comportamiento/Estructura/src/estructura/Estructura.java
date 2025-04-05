
package estructura;


public class Estructura {

    
    public static void main(String[] args) {
        
        
        CuentaCampus cuentaCalificaciones = new Cuenta();
        
        
         CuentaNotas cuentaNotas = new CuentaNotas(cuentaCalificaciones);
        
        cuentaNotas.abrirCuenta();
        cuentaNotas.cargarNota();
    }
    
}
