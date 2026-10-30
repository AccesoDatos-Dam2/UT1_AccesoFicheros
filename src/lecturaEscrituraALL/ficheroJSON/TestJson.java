package lecturaEscrituraALL.ficheroJSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class TestJson {
	public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(1, "Teclado", 45.99));
        productos.add(new Producto(2, "Ratón", 25.00));

        // Escribir a JSON
        try (Writer writer = new FileWriter("productos.json")) {
            gson.toJson(productos, writer);
            System.out.println("JSON generado.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Leer de JSON
        try (Reader reader = new FileReader("productos.json")) {
        	// 1. Deserializamos directamente a un array de Producto
            Producto[] arrayLeido = gson.fromJson(reader, Producto[].class);
            
            // 2. Convertimos el array a una List si necesitas seguir usándola como lista
            List<Producto> listaLeida = java.util.Arrays.asList(arrayLeido);

            for (Producto p : listaLeida) {
                System.out.println("Producto: " + p.nombre + " - Precio: " + p.precio + " €");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
