package lecturaEscrituraALL.ficheroXML;



import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class TestXml {
	public static void main(String[] args) {
        try {
            File archivo = new File("pokedex.xml");
            if (!archivo.exists()) {
                System.out.println("Crea un fichero 'catalogo.xml' básico para probar.");
                return;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pelicula");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String titulo = element.getElementsByTagName("nombre").item(0).getTextContent();
                    System.out.println("Película leída del XML: " + titulo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
