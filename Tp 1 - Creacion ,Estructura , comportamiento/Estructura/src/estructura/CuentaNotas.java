
package estructura;

class CuentaNotas implements CuentaCampus{     
    
    private CuentaCampus cuentaCampus; // Aquí usaremos CuentaCampus como tipo

    public CuentaNotas(CuentaCampus cuentaCampus) {
        this.cuentaCampus = cuentaCampus;
    }
        
     @Override
    public void abrirCuenta(){
        cuentaCampus.abrirCuenta(); // Llamamos al método de la cuenta original
        System.out.println("-------------------");
        System.out.println("Cuenta del campus con las notas abierta: ");
        System.out.println("");
        
    }
    
    public void cargarNota(){
        System.out.println("Nota cargada");
        System.out.println("");
    }
    
    
    
}
