package boletin1.ejercicio2;

import java.io.Serializable;

public class Personaje implements Serializable{
	private static final long serialVersionUID=1L;
	private String nombre;
	private String anime;
	
	public Personaje(String nombre,String anime) {
		this.nombre=nombre;
		this.anime=anime;
		
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getAnime() {
		return anime;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAnime(String anime) {
		this.anime = anime;
	}

	
}
