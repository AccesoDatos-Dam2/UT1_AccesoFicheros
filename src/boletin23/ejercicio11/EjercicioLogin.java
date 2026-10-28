package boletin23.ejercicio11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EjercicioLogin {
	
	public static boolean validarUsuarios(String rutaFichero,String usuarioBuscar,String passBuscar) {
		File f= new File(rutaFichero);
		
		// 1. Validar que el fichero existe
		if(!f.exists()) {
			System.out.println("ERROR: El Fichero no existe");
			return false;
		}
		
		
		// 2. Leer línea a línea
		try(BufferedReader br=new BufferedReader(new FileReader(f))){
			String linea;
			while((linea=br.readLine())!=null) {
				linea=linea.trim();
				if(linea.isEmpty()) continue;
				
				// 3. Separar por los dos puntos ":"
				String[] partes=linea.split(":");
				if(partes.length==2) {
					String usuarioFichero=partes[0];
					String passFichero=partes[1];
					
					// Si coinciden usuario y contraseña, login exitoso
					if(usuarioFichero.equals(usuarioBuscar)&& passFichero.equals(passBuscar)) {
						return true;
					}
				}
			}
		}catch(IOException e) {
			System.out.println("Error al leer el fichero: "+e.getMessage());
		}
		// 4. Si recorre todo el archivo sin encontrar coincidencia
		return false;
	}
	
	public static void main(String[] args) {
		File archivo= new File("usuarios.txt");
		
		// Probamos la función con un usuario de ejemplo
		boolean esValido=validarUsuarios(archivo.getAbsolutePath(),"admin","1234");
		
		if(esValido) {
			System.out.println("Login correcto");
		}else {
			System.out.println("Usuario o contrasena incorrectos");
		}
	}
}
