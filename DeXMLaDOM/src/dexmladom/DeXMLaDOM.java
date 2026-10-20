/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dexmladom;

import java.io.File;

/**
 *
 * @author Alejandro
 */
public class DeXMLaDOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TiendaMoviles tienda = new TiendaMoviles();
        int resultado = tienda.abrirDocumento(new File("TiendaMoviles.xml"));
        System.out.println("Fichero abierto " + resultado);
        tienda.mostrarMoviles();
        tienda.buscarPorMarca("Apple");
    }
    
}
