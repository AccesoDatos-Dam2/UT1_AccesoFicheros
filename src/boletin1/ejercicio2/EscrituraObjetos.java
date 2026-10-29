package boletin1.ejercicio2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EscrituraObjetos {
	public static void guardarPersonajes(List<Personaje>lista,String ruta) {
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(ruta))){
	 		for(Personaje p:lista) {
	 			oos.writeObject(p);
	 		}
	 		System.out.println("Objetos Guardados correctamente en "+ruta);
		}catch(IOException e) {
			System.err.println("Error al escribir objetos: "+e.getMessage()); 
		}
	}
	public static void leerPersonajes(String ruta) {
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(ruta))){
			while(true) {
				Personaje p=(Personaje) ois.readObject();
				System.out.println(p.getNombre()+" ("+p.getAnime()+")");
			}
		}catch(IOException e) {
			System.out.println("Fin de la lectura del fichero");
		} catch (ClassNotFoundException e) {
			System.err.println("Error al leer el fichero binario: "+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		String ruta="personajes.dat";
		
		List<Personaje> personajes=new ArrayList<>();
		personajes.add(new Personaje("Roronoa Zoro","One Piece"));
		personajes.add(new Personaje("Vinsmoke Sanji","One Piece"));
		personajes.add(new Personaje("Tanjiro Kamado", "Demon Slayer"));
		personajes.add(new Personaje("Naruto Uzumaki", "Naruto"));
		personajes.add(new Personaje("Ken Takakura", "Dan Da Dan"));
	
		guardarPersonajes(personajes,ruta);
		System.out.println("Cargando datos desde "+ruta+":");
		leerPersonajes(ruta);
	}
}
