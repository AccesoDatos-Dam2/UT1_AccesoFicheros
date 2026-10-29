package boletin24.ejercicio7;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class RegistrarUsuario {

	public static boolean registrarUsuario(String rutaFichero,String nuevoUsuario,String pass1,String pass2) {
		//1.Validacion de coincidencia de las 2 contraseñas
		if(!pass1.equals(pass2)) {
			System.out.println("Las contraseñas no coinciden");
			return false;
		}
		//2.Abrimos el fichero en modo append con el true
		try(PrintWriter pw=new PrintWriter(new FileWriter(rutaFichero,true))){
			
			//3.Escribir la linea y retorna true
			pw.println(nuevoUsuario + ":"+ pass1);
			return true;//devuelve true si se guardo correctamente
			
		}catch(IOException e) {
			System.err.println("Error al escribir en el fichero: "+e.getMessage());
			return false;
		}
	}
	
	public static void main(String[] args) {
		
		// Prueba con contraseñas que NO coinciden (muestra error y devuelve false)
		registrarUsuario("usuarios.txt","Adrian","1234","4321");
		// Prueba correcta (escribe adrian:1234 al final del fichero)
		boolean exito=registrarUsuario("usuarios.txt","Adrian","1234","1234");
		if(exito) {
			System.out.println("Usuario registrado con exito");
		}
	}
}
