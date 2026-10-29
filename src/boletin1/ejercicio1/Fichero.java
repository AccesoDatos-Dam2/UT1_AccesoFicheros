package boletin1.ejercicio1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Fichero {
	// Método que carga los animes desde un fichero y devuelve un Map (código -> título)
	public static Map<Integer,String> cargarAnimes(String rutaFichero){
		// Crea un TreeMap vacío: guarda pares código/título y los mantiene ordenados por código
		Map<Integer,String> animes=new TreeMap<>();

		// try-with-resources: abre el fichero para leerlo y lo cierra solo al terminar
		try(BufferedReader br= new BufferedReader(new FileReader(rutaFichero))){
			// Variable que guardará cada línea leída del fichero
			String linea;
			// Lee línea a línea; readLine() devuelve null cuando se acaba el fichero
			while((linea=br.readLine())!=null) {
				// Quita los espacios al principio y al final de la línea
				linea=linea.trim();
				// Si la línea está vacía, salta a la siguiente iteración
				if(linea.isEmpty()) continue;

				// Divide la línea en 2 partes máximo por el primer espacio: [código, título]
				String[] partes=linea.split(" ", 2);
				// Convierte la primera parte (texto) a número entero: es el código del anime
				int codigo=Integer.parseInt(partes[0]);
				// La segunda parte es el título completo del anime (puede tener espacios)
				String titulo=partes[1];

				// Añade el par código/título al mapa
				animes.put(codigo, titulo); //añade a el elemento en el map
			}
		// Captura errores de lectura (fichero no encontrado, fallo al leer...)
		}catch(IOException e) {
			// Muestra un mensaje de error con la causa
			System.out.println("Error al leer fichero de animes: "+e.getMessage());
		}
		// Devuelve el mapa con todos los animes cargados
		return animes;
	}

	// Método que carga los personajes y los agrupa por código de anime (código -> lista de nombres)
	public static Map<Integer,List<String>> cargarPersonajes(String rutaFichero){
		// HashMap vacío: cada código de anime tendrá asociada una lista de personajes (sin orden garantizado)
		Map<Integer,List<String>> personajesPorCodigo=new HashMap<>();

		// Abre el fichero de personajes con BufferedReader; se cierra automáticamente al terminar
		try(BufferedReader br=new BufferedReader(new FileReader(rutaFichero))){
			// Variable que guardará cada línea leída
			String linea;
			// Lee el fichero línea a línea hasta que no queden más (null)
			while((linea=br.readLine())!=null) {
				// Elimina espacios sobrantes al inicio y al final
				linea=linea.trim();
				// Ignora las líneas vacías
				if(linea.isEmpty()) continue;
				// Separa en dos partes por el primer espacio: [código, nombre]
				String[] partes=linea.split(" ", 2);
				// Convierte el código de texto a entero: indica a qué anime pertenece el personaje
				int codigo=Integer.parseInt(partes[0]);
				// El resto de la línea es el nombre del personaje
				String nombre=partes[1];

				// Si todavía no hay lista para este código de anime...
				if(!personajesPorCodigo.containsKey(codigo)) {
					// ...crea una lista nueva y vacía asociada a ese código
					personajesPorCodigo.put(codigo,new ArrayList<>());
				}
				// Obtiene la lista de ese código y le añade el personaje
				personajesPorCodigo.get(codigo).add(nombre);
			}

		// Captura errores de entrada/salida al leer el fichero
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// Imprime la traza completa del error por consola
			e.printStackTrace();
		}
		// Devuelve el mapa con los personajes agrupados por código de anime
		return personajesPorCodigo;
	}

		// Método para cruzar la información e imprimir el formato final
		public static void mostrarResultado(Map<Integer, String> animes, Map<Integer, List<String>> personajes) {
			// 1. Recorremos los animes en orden
			// Itera sobre cada entrada (código/título) del mapa de animes; al ser TreeMap, va ordenado por código
			for (Map.Entry<Integer, String> entry : animes.entrySet()) {
				// Obtiene el código del anime actual (la clave)
				int codigoAnime = entry.getKey();
				// Obtiene el título del anime actual (el valor)
				String tituloAnime = entry.getValue();

				// Imprime el título del anime
				System.out.println(tituloAnime);

				// Comprobamos si hay personajes asignados a este anime
				// Cierto si existe el código en el mapa de personajes y su lista no está vacía
				if (personajes.containsKey(codigoAnime) && !personajes.get(codigoAnime).isEmpty()) {
					// Recorre cada personaje de la lista de ese anime
					for (String personaje : personajes.get(codigoAnime)) {
						// Imprime el personaje precedido de un guion
						System.out.println(" - " + personaje);
					}
				// Si no hay personajes para este anime...
				} else {
					// ...imprime un mensaje indicándolo
					System.out.println(" - No hay personajes");
				}
				// Imprime una línea en blanco para separar un anime del siguiente
				System.out.println(); // Línea en blanco de separación
			}

			// 2. Buscamos personajes cuyo código no exista en el fichero de animes
			// Lista donde se guardarán los personajes "huérfanos" (su código no está en animes)
			List<String> personajesSinAnime = new ArrayList<>();
			// Recorre cada entrada (código/lista de personajes) del mapa de personajes
			for (Map.Entry<Integer, List<String>> entry : personajes.entrySet()) {
				// Obtiene el código de anime asociado a ese grupo de personajes
				int codigoPersonaje = entry.getKey();
				// Si ese código NO existe en el mapa de animes...
				if (!animes.containsKey(codigoPersonaje)) {
					// ...añade todos los personajes de ese código a la lista de huérfanos
					personajesSinAnime.addAll(entry.getValue());
				}
			}

			// Imprimimos los personajes huérfanos si los hay
			// Solo entra si la lista de huérfanos tiene al menos un personaje
			if (!personajesSinAnime.isEmpty()) {
				// Imprime el encabezado de la sección
				System.out.println("Personajes sin anime");
				// Recorre cada personaje huérfano
				for (String personaje : personajesSinAnime) {
					// Lo imprime precedido de un guion
					System.out.println(" - " + personaje);
				}
			}
		}


	// Método principal: punto de entrada del programa
	public static void main(String[] args) {
		// Objeto File con la ruta del fichero de personajes
		File fPers = new File("personajes.txt");
		// Objeto File con la ruta del fichero de animes
	    File fAnime = new File("animes.txt");

	    //cargarDatos
	    // Carga los animes del fichero en un mapa (código -> título), pasando la ruta absoluta
	    Map<Integer,String> animes= cargarAnimes(fAnime.getAbsolutePath());
	    // Carga los personajes del fichero en un mapa (código -> lista de nombres)
	    Map<Integer,List<String>> personajes=cargarPersonajes(fPers.getAbsolutePath());

	    // Cruza ambos mapas e imprime cada anime con sus personajes y, al final, los personajes sin anime
	    mostrarResultado(animes,personajes);

	}
    /*public static ArrayList<String> obtenerPersonajesPorCodigo(int codigoBuscado, File ficheroPersonajes) {
        ArrayList<String> personajes = new ArrayList<>();
        try (Scanner sc = new Scanner(ficheroPersonajes)) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty()) continue;

                int esp = linea.indexOf(" ");
                if (esp == -1) continue;

                int codigo = Integer.parseInt(linea.substring(0, esp));
                String nombre = linea.substring(esp + 1);

                if (codigo == codigoBuscado) {
                    personajes.add(nombre);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el fichero de personajes: " + e.getMessage());
        }

        // Ordenar los personajes alfabéticamente (A-Z)
        Collections.sort(personajes);
        return personajes;
    }

    public static void main(String[] args) {
        File fPers = new File("/home/alumno/IdeaProjects/githubAccesoDatos/Ficheros/src/Boletin1/Ejercicio1/personajes.txt");
        File fAnime = new File("/home/alumno/IdeaProjects/githubAccesoDatos/Ficheros/src/Boletin1/Ejercicio1/animes.txt");

        try {
            // 1. Leer el fichero de animes y crear un diccionario (TreeMap ordena automáticamente por la clave numérica)
            TreeMap<Integer, String> animes = new TreeMap<>();
            Scanner scAnime = new Scanner(fAnime);
            while (scAnime.hasNextLine()) {
                String linea = scAnime.nextLine().trim();
                if (linea.isEmpty()) continue;

                int esp = linea.indexOf(" ");
                if (esp == -1) continue;

                int id = Integer.parseInt(linea.substring(0, esp));
                String nombre = linea.substring(esp + 1);
                animes.put(id, nombre);
            }
            scAnime.close();

            // 2. Por cada entrada del diccionario, buscar los personajes usando la función modular
            for (Map.Entry<Integer, String> entry : animes.entrySet()) {
                int idAnime = entry.getKey();
                String nombreAnime = entry.getValue();

                System.out.println(nombreAnime);
                ArrayList<String> personajes = obtenerPersonajesPorCodigo(idAnime, fPers);

                if (personajes.isEmpty()) {
                    System.out.println("- No hay personajes");
                } else {
                    for (String p : personajes) {
                        System.out.println("- " + p);
                    }
                }
            }

            // 3. Recorrer por última vez el fichero para encontrar personajes cuyo código no esté en el diccionario
            ArrayList<String> personajesSinAnime = new ArrayList<>();
            Scanner scPers = new Scanner(fPers);
            while (scPers.hasNextLine()) {
                String linea = scPers.nextLine().trim();
                if (linea.isEmpty()) continue;

                int esp = linea.indexOf(" ");
                if (esp == -1) continue;

                int idPers = Integer.parseInt(linea.substring(0, esp));
                String nombrePers = linea.substring(esp + 1);

                if (!animes.containsKey(idPers)) {
                    personajesSinAnime.add(nombrePers);
                }
            }
            scPers.close();

            // Si hay personajes sin anime, se muestran ordenados alfabéticamente
            if (!personajesSinAnime.isEmpty()) {
                Collections.sort(personajesSinAnime);
                System.out.println("Personajes sin anime");
                for (String p : personajesSinAnime) {
                    System.out.println("- " + p);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontró alguno de los ficheros. " + e.getMessage());
        }
    }*/
}
