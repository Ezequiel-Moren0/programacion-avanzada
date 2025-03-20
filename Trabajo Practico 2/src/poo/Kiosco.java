
package poo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kiosco {
    
    private static List<Verduras> listaVerduras = new ArrayList<>();
    private static List<Galletas> listaGalletas = new ArrayList<>();
    private static List<Frutas> listaFrutas = new ArrayList<>();
  
    
    
    public static void main(String[] args) {

        int op = 0;
        Scanner leer= new Scanner(System.in);
        
         listaVerduras.add(new Verduras("Zanahoria", 1360.5));
         listaVerduras.add(new Verduras("Papa", 2000.0));
         listaVerduras.add(new Verduras("Camote",2000.0));
        
         listaGalletas.add(new Galletas("Oreo", 1500.0));
         listaGalletas.add(new Galletas("Melbas", 1000.0));
         listaGalletas.add(new Galletas("Sonrisas",1500.0));
         
                 
         listaFrutas.add(new Frutas("Manzana",800.0));
         listaFrutas.add(new Frutas("Banana",500.0));
         listaFrutas.add(new Frutas("Durazno", 1500.0));
         
         
                 
         
        do{
            do{
                System.out.println("----------------------");
                System.out.println("Bienvenidos al kiosco");
                System.out.println("1.-Verduras");
                System.out.println("2.-Galletas");
                System.out.println("3.-Frutas");
                System.out.println("4.-Salir");
                System.out.println("Ingrese una op");
                op=leer.nextInt();
            
            }while(op<1||op>4);
        
         switch(op){
                    
             case 1:
                 mostrarVerduras();
                 break;
                 
             case 2:
                 mostrarGalletas();
                 break;
                 
             case 3:
                 mostrarFrutas();
                 break;
                 
             case 4:
                 
                 System.out.println("Adios");
                 System.exit(0);
                 break;
                     
                     
                     
         }
         
    }while (op !=4);
    
    }
    
     private static void mostrarVerduras() {
        System.out.println("\nLista de Verduras:");
        for (Verduras v : listaVerduras) {
            
            System.out.println(v);
            
        }
    }
    
     private static void mostrarGalletas(){
         System.out.println("\nLista de Galletas");
         
         for(Galletas g : listaGalletas){
             
             System.out.println(g);
             
         }
         
     }
     
     
     private static void mostrarFrutas(){
         System.out.println("\nLista de Frutas"); 
         for(Frutas f : listaFrutas)
             
             System.out.println(f);
         
     }
    
}
