package combinaciones.csvxmldom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CsvToXmlDom {
	// Método encargado de leer el CSV y transformarlo en un fichero XML mediante DOM
    public static void convertirCsvAXml(String rutaCsv, String rutaXml) {
        
        // 1. Validar la existencia física del fichero CSV de origen
        File f = new File(rutaCsv);
        if (!f.exists()) {
            System.out.println("El fichero CSV no existe.");
            return;
        }

        try {
            // 2. A) Inicializar la fábrica y el constructor de documentos DOM en memoria
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument(); // Crea el árbol XML vacío en memoria

            // 3. Crear y adjuntar el Elemento Raíz del XML (<articulos>)
            Element raiz = doc.createElement("articulos");
            doc.appendChild(raiz);

            // 4. B) Leer el CSV línea por línea usando BufferedReader
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    
                    // Separar los campos de la línea delimitados por punto y coma (;)
                    String[] partes = linea.split(";");
                    if (partes.length >= 3) {
                        
                        // Crear el nodo contenedor <articulo> para cada línea del CSV
                        Element articuloElem = doc.createElement("articulo");

                        // Crear y rellenar el subnodo <referencia>
                        Element refElem = doc.createElement("referencia");
                        refElem.setTextContent(partes[0].trim());
                        articuloElem.appendChild(refElem);

                        // Crear y rellenar el subnodo <descripcion>
                        Element descElem = doc.createElement("descripcion");
                        descElem.setTextContent(partes[1].trim());
                        articuloElem.appendChild(descElem);

                        // Crear y rellenar el subnodo <stock>
                        Element stockElem = doc.createElement("stock");
                        stockElem.setTextContent(partes[2].trim());
                        articuloElem.appendChild(stockElem);

                        // Colgar el nodo <articulo> dentro de la raíz (<articulos>)
                        raiz.appendChild(articuloElem);
                    }
                }
            }

            // 5. C) Volcar el documento DOM de la memoria a un archivo físico XML (Transformer)
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Opcional pero recomendado: Aplicar formato bonito con sangría (indentación de 4 espacios)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Definir el origen de los datos (el DOM) y el destino (el fichero XML físico)
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(rutaXml));
            
            // Ejecutar la transformación final
            transformer.transform(source, result);

            System.out.println("¡Fichero XML generado con éxito en: " + rutaXml + "!");

        } catch (Exception e) {
            // Captura de cualquier error durante el parseo, E/S o transformación
            System.err.println("Error procesando el XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método main para probar la ejecución local
    public static void main(String[] args) {
        convertirCsvAXml("paises.csv", "articulos.xml");
    }
}
