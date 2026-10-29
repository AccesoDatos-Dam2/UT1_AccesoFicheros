package boletin1.ejercicio3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InformePaises {

	public static String formatearLista(List<String> elementos) {
		if (elementos.isEmpty()) {
			return "";
		}
		if(elementos.size()==1) {
			return elementos.get(0);
		}
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<elementos.size();i++) {
			if(i==elementos.size()-1) {
				sb.append(" y ").append(elementos.get(i));//ultimo elemento
			} else if(i==0) {
				sb.append(elementos.get(i));
			}else {
				sb.append(", ").append(elementos.get(i));
			}
		}
		return sb.toString();
	}
	
	public static void procesarFichero(String rutaFichero) {
		File f=new File(rutaFichero);
		if(!f.exists()) {
			System.out.println("El Fichero no existe");
			return;
		}
		List<String> paises=new ArrayList<>();
		List<String> capitales=new ArrayList<>();
		List<String> monedas=new ArrayList<>();
		List<String> animales=new ArrayList<>();
				
		try(BufferedReader br=new BufferedReader(new FileReader(f))){
			String cabecera= br.readLine();
			
			String linea;
			while((linea=br.readLine())!=null) {
				linea=linea.trim();
				if(linea.isEmpty()) continue;
				
				String[] partes=linea.split(",");
				
				if(partes.length==4) {
					paises.add(partes[0].trim());
					capitales.add(partes[1].trim());
					monedas.add(partes[2].trim());
					animales.add(partes[3].trim());
				}
			}
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}
		if(paises.isEmpty()) {
			System.out.println("No hay ningun pais");
		}else {
			System.out.println("Paises en el fichero: "+paises.size());
			System.out.println("Nombres: "+formatearLista(paises));
			System.out.println("Las capitales de los mismos son: "+formatearLista(capitales));
			System.out.println("Sus monedas oficiales son: "+formatearLista(monedas));
			System.out.println("Sus animales mas representativos son "+formatearLista(animales));
		}
	}
	public static void main(String[] args) {
		procesarFichero("paises.csv");

	}

}
