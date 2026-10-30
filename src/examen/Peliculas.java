package examen;

public class Peliculas {
	
	private String titulo;
	private int anio;
	private int fase;
	private String director;
	
	public Peliculas(String titulo, int anio, int fase, String director) {
		this.titulo = titulo;
		this.anio = anio;
		this.fase = fase;
		this.director = director;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

}
