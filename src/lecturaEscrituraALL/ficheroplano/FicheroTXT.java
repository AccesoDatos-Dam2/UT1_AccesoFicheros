package lecturaEscrituraALL.ficheroplano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FicheroTXT {
	public static void main(String[] args) {
        // 1. Escritura inicial
        try (PrintWriter pw = new PrintWriter(new FileWriter("alumnos.txt"))) {
            pw.println("Ana;20;8.5");
            pw.println("Luis;22;6.0");
            pw.println("Pedro;21;9.2");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. Lectura y filtrado (Nota > 8.0)
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"));
             PrintWriter pwDestacados = new PrintWriter(new FileWriter("destacados.txt"))) {
            
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                double nota = Double.parseDouble(partes[2]);
                
                if (nota > 8.0) {
                    pwDestacados.println(linea); // Guardar en el nuevo fichero
                }
            }
            System.out.println("Fichero de texto procesado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
