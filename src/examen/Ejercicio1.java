package examen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Ejercicio1 {
	
	// Devuelve el texto de la primera etiqueta con ese nombre (o "" si no existe)
		private static String texto(Element padre, String etiqueta) {
			NodeList nl = padre.getElementsByTagName(etiqueta);
			if (nl.getLength() == 0) {
				return "";
			}
			return nl.item(0).getTextContent().trim();
		}
	 
		// Un pokemon puede tener varios <tipo>: los une con "/" (ej. fuego/volador)
		private static String tipos(Element padre) {
			NodeList nl = padre.getElementsByTagName("tipo");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < nl.getLength(); i++) {
				if (i > 0) {
					sb.append("/");
				}
				sb.append(nl.item(i).getTextContent().trim());
			}
			return sb.toString();
		}
		
	public static void leerXML(String ficheroXml,String ficheroTxt) throws ParserConfigurationException, SAXException, IOException {
		File xml = new File(ficheroXml);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(ficheroXml);
        
        doc.getDocumentElement().normalize();
        
        NodeList listaPokemon=doc.getElementsByTagName("pokemon");
		
        try (PrintWriter pw = new PrintWriter(ficheroTxt)) {
			pw.println("=================Pokedex=====================");
			for(int i=0;i<listaPokemon.getLength();i++) {
				Node node=listaPokemon.item(i); 
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element pokemon= (Element)node;
				String id=pokemon.getAttribute("id");
				String nombre=texto(pokemon,"nombre");
				String tipo= tipos(pokemon);
				
				pw.println("#" + id + " " + nombre + "  " + tipo);
				}
			}
        }
	}
	
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		leerXML("pokedex.xml","pokemon.txt");	 
	
		
		
		
		
 }
}
