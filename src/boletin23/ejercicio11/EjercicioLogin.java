package boletin23.ejercicio11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EjercicioLogin {
	
	public static boolean validarUsuarios(String rutaFichero,String usuarioBuscar,String passBuscar) {
		File f= new File(rutaFichero);
		if(!f.exists()) {
			System.out.println("ERROR: El Fichero no existe");
			return false;
		}
		
		try(BufferedReader br=new BufferedReader(new FileReader(f))){
			String linea;
			while((linea=br.readLine())!=null) {
				linea=linea.trim();
				if(linea.isEmpty()) continue;
				
				String[] partes=linea.split(":");
				if(partes.length==2) {
					String usuarioFichero=partes[0];
					String passFichero=partes[1];
					
					if(usuarioFichero.equals(usuarioBuscar)&& passFichero.equals(passBuscar)) {
						return true;
					}
				}
			}
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}
		return false;
	}
	
	public static void main(String[] args) {
		File archivo= new File("usuarios.txt");
		
		boolean esValido=validarUsuarios(archivo.getAbsolutePath(),"admin","1234");
		
		if(esValido) {
			System.out.println("Login correcto");
		}else {
			System.out.println("Usuario o contrasena incorrectos");
		}
	}
}
