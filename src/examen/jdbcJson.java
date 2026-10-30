package examen;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


public class jdbcJson {
	private static final String URL = "jdbc:mysql://localhost:3306/dam2"; 
    private static final String USER = "profe"; 
    private static final String PASS = "abc123"; 
    
    public static void importarPeliculasDesdeJson(String rutaJson) { 
        File f = new File(rutaJson); 
        if (!f.exists()) { 
            System.out.println("El fichero JSON no existe."); 
            return; 
        } 

        // Continuación desde List:
        List<Peliculas> listaPeliculas = null;
        Gson gson = new Gson();

        // 1. Leer y parsear el fichero JSON con Gson
        try (Reader reader = new FileReader(f)) {
        	JsonObject raiz=gson.fromJson(reader, JsonObject.class);
            Type tipoLista = new TypeToken<List<Peliculas>>(){}.getType();
            listaPeliculas = gson.fromJson(raiz.getAsJsonArray("ucm"), tipoLista);
        } catch (IOException e) {
            System.err.println("Error leyendo el fichero JSON: " + e.getMessage());
            e.printStackTrace();
            return;
        }
       
        String sql1 = "CREATE TABLE IF NOT EXISTS peliculas("
        		+ "titulo VARCHAR(150) primary key,"
        		+ "anio INT NOT NULL,"
        		+ "fase INT NOT NULL,"
        		+ "director VARCHAR(100) NOT NULL"
        		+ ");";
        
        String sql2 = "INSERT IGNORE INTO peliculas (titulo, anio, fase, director) VALUES (?, ?, ?, ?)";
     // 2. Insertar los datos en la Base de Datos mediante JDBC (con Transacción)
        try (Connection conn = DriverManager.getConnection(URL,USER,PASS)) {
            
        	// Crear la tabla (el DDL en MySQL hace commit implícito, por eso va antes)
        	try (Statement st = conn.createStatement()) {
        		st.executeUpdate(sql1);
       		}
        	 
        	conn.setAutoCommit(false);
        	
            try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                for (Peliculas p : listaPeliculas) {
                    pstmt2.setString(1, p.getTitulo());
                    pstmt2.setInt(2,p.getAnio());
                    pstmt2.setInt(3,p.getFase());
                    pstmt2.setString(4,p.getDirector());
                    
                    pstmt2.executeUpdate();
                }

                conn.commit(); // Confirmar cambios si todo va bien
                System.out.println("¡Peliculas importados desde JSON a la BD con éxito!");

            } catch (SQLException e) {
                conn.rollback(); // Si falla algo, deshacemos los cambios
                System.err.println("Error en la inserción, haciendo Rollback...");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        importarPeliculasDesdeJson("marvel.json");
    }
}
