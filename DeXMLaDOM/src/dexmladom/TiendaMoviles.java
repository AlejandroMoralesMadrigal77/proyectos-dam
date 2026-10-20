/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dexmladom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alejandro
 */
public class TiendaMoviles {

    Document doc;

    public int abrirDocumento(File f) {
        try {
            System.out.println("Abriendo archivo XML file y generando DOM ....");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            System.out.println("DOM creado con éxito.");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public void guardarDocumento(String nuevoArchivo) {
        try {
            Source src = new DOMSource(doc);
            StreamResult rst = new StreamResult(new File(nuevoArchivo));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("Archivo creado del DOM con éxito");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int mostrarMoviles() {
        try {
            Node raiz = doc.getFirstChild();
            NodeList hijos = raiz.getChildNodes();
            for (int i = 0; i < hijos.getLength(); i++) {
                Node nodo = hijos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE && nodo.getNodeName().equals("Movil")) {
                    Element elementoMovil = (Element) nodo;
                    String marca = elementoMovil.getAttribute("marca");
                    String estado = elementoMovil.getAttribute("estado");

                    String modelo = "";
                    String almacenamiento = "";
                    String precio = "";

                    NodeList listaMoviles = nodo.getChildNodes();
                    for (int j = 0; j < listaMoviles.getLength(); j++) {

                        Node datos = listaMoviles.item(j);
                        if (datos.getNodeType() == Node.ELEMENT_NODE) {
                            String nombreEtiqueta = datos.getNodeName();

                            if (nombreEtiqueta.equalsIgnoreCase("Modelo")) {
                                modelo = datos.getTextContent();
                            } else if (nombreEtiqueta.equalsIgnoreCase("Almacenamiento")) {
                                almacenamiento = datos.getTextContent();
                            } else if (nombreEtiqueta.equalsIgnoreCase("Precio")) {
                                precio = datos.getTextContent();
                            }
                        }
                    }

                    System.out.println("Móvil: " + modelo + " | Marca: " + marca + " | Estado: " + estado
                            + " | Capacidad: " + almacenamiento + " | Precio: " + precio + "€");
                }
            }
            return 0;

        } catch (DOMException e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
    }

    public int buscarPorMarca(String marcaBuscada) {
        try {
            Node raiz = doc.getFirstChild();
            NodeList hijos = raiz.getChildNodes();
            boolean encontrado = false;
            for (int i = 0; i < hijos.getLength(); i++) {
                Node nodo = hijos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE && nodo.getNodeName().equalsIgnoreCase("Movil")) {
                    Element elemento = (Element) nodo;
                    String marca = elemento.getAttribute("marca");
                    if (marca.equalsIgnoreCase(marcaBuscada)) {

                        encontrado = true;
                        String modelo = "";
                        String precio = "";
                        
                        NodeList datosMovil = nodo.getChildNodes();
                        for(int j= 0; j<datosMovil.getLength();j++){
                        Node datos = datosMovil.item(j);
                        if(datos.getNodeType()== Node.ELEMENT_NODE){
                        if(datos.getNodeName().equalsIgnoreCase("modelo")){
                        modelo = datos.getTextContent();
                        
                        }else if(datos.getNodeName().equalsIgnoreCase("precio")){
                            precio = datos.getTextContent();
                        
                        }
                        
                       
                        }
                        
                        }
                        System.out.println("modelo encontrado>: " + modelo + "precio: " + precio);
                        return 0;
                    }
                }

            }
            if (encontrado == false) {
            System.out.println("Marca no encontrada en el catálogo.");
            return -1;
        }

        } catch (DOMException e) {

            System.out.println("error" + e.getMessage());

        }
        return -2;
    }
}
