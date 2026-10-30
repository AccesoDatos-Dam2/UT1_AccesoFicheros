package combinaciones.txtJson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FicheroTOJSON {
	public static void exportarTxtAJson(String rutaTxt, String rutaJson) {
        File archivo = new File(rutaTxt);
        if (!archivo.exists()) {
            System.out.println("El fichero " + rutaTxt + " no existe.");
            return;
        }

        // Continuación desde List:
        List<Articulo> listaArticulos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 3) {
                    String referencia = partes[0].trim();
                    String descripcion = partes[1].trim();
                    int stock = Integer.parseInt(partes[2].trim());
                    
                    listaArticulos.add(new Articulo(referencia, descripcion, stock));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Escribir el resultado a formato JSON con Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(rutaJson)) {
            gson.toJson(listaArticulos, writer);
            System.out.println("¡Exportado correctamente a JSON!");
        } catch (IOException e) {
            System.err.println("Error al escribir el JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        exportarTxtAJson("articulos.txt", "articulos.json");
    }
}
