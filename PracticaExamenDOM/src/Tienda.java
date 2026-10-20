
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Alejandro
 */
public class Tienda {

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

    public int buscarPorTitulo(String titulo) {
        Node raiz = doc.getFirstChild();
        NodeList hijos = raiz.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node nodoJuego = hijos.item(i);
            if (nodoJuego.getNodeType() == Node.ELEMENT_NODE && nodoJuego.getNodeName().equalsIgnoreCase("Videojuego")) {
                NodeList datosVideojuegos = nodoJuego.getChildNodes();

                for (int j = 0; j < datosVideojuegos.getLength(); j++) {
                    Node datoJuego = datosVideojuegos.item(i);
                    if (datoJuego.getNodeName().equalsIgnoreCase("Titulo")) {

                        if (datoJuego.getTextContent().equalsIgnoreCase(titulo)) {
                            System.out.println("videojuego encontrado " + titulo);
                            return 0;

                        }
                    }

                }

            }

        }

        System.out.println("Videojuego no encontrado");
        return -1;
    }

    public int actualizarStock(String tituloBuscado, String nuevoStock) {
        Node raiz = doc.getFirstChild();
        NodeList hijos = raiz.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            Node nodo = hijos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE && nodo.getNodeName().equalsIgnoreCase("Videojuego")) {
                String tituloActual = "";
                Node nodoStockActual = null;
                NodeList datos = nodo.getChildNodes();
                for (int j = 0; j < datos.getLength(); j++) {
                    Node datosJuego = datos.item(j);
                    if (datosJuego.getNodeName().equalsIgnoreCase("Titulo")) {
                        tituloActual = datosJuego.getTextContent();

                    }
                    if (datosJuego.getNodeName().equalsIgnoreCase("Stock")) {
                        nodoStockActual = datosJuego;

                    }

                }
                if (tituloActual.equalsIgnoreCase(tituloBuscado) && nodoStockActual != null) {

                    nodoStockActual.setTextContent(nuevoStock);

                }
                System.out.println("Stock cambiado con exito");

                return 0;

            }

        }
        System.out.println("videojuego nio encontrado");
        return -1;
    }

    public void insertarVideojuego(String titulo, String precio, String stock, String plataforma) {
try{
        Element nuevoJuego = doc.createElement("Videojuego");
        nuevoJuego.setAttribute("plataforma", plataforma);
        Element nodoTitulo = doc.createElement("Titulo");
        Element nodoPrecio = doc.createElement("Precio");
        Element nodoStock = doc.createElement("Stock");
        nodoTitulo.setTextContent(titulo);
        nodoPrecio.setTextContent(precio);
        nodoStock.setTextContent(stock);
        nuevoJuego.appendChild(nodoTitulo);
        nuevoJuego.appendChild(nodoPrecio);
                nuevoJuego.appendChild(nodoStock);
                Node raiz = doc.getFirstChild();
                raiz.appendChild(nuevoJuego);
                System.out.println("Videojuego Añadido correctamente");
}catch(DOMException e){
    System.out.println("Error al añadir el juego "+ e.getMessage());
}
    }

}
