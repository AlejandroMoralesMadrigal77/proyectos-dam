
import java.io.File;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Alejandro
 */
public class AccesoVideojuegos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tienda t= new Tienda();
        int resultado = t.abrirDocumento(new File ("Tienda.xml"));
        System.out.println("Fichero abierto " + resultado);
        t.buscarPorTitulo("Spider-Man 2");
       
        int resultado2 =  t.actualizarStock("Spider-Man 2", "5");
        if(resultado2==0){
        t.guardarDocumento("Tienda.xml");
            System.out.println("Cambios guardados");
        
        }else{
            System.out.println("No hay cambios que guardar");
        }
        
        t.insertarVideojuego("Teken", "30", "4", "PSOne");
        t.guardarDocumento("Tienda.xml");
        
    }
    
}
