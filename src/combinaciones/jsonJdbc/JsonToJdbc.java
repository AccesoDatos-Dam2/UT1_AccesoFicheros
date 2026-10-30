package combinaciones.jsonJdbc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonToJdbc {
	private static final String URL = "jdbc:mysql://localhost:3306/mi_base_datos"; 
    private static final String USER = "root"; 
    private static final String PASS = "abc123"; 

    public static void importarUsuariosDesdeJson(String rutaJson) { 
        File f = new File(rutaJson); 
        if (!f.exists()) { 
            System.out.println("El fichero JSON no existe."); 
            return; 
        } 

        // Continuación desde List:
        List<Usuario> listaUsuarios = null;
        Gson gson = new Gson();

        // 1. Leer y parsear el fichero JSON con Gson
        try (Reader reader = new FileReader(f)) {
            Type tipoLista = new TypeToken<List<Usuario>>(){}.getType();
            listaUsuarios = gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.err.println("Error leyendo el fichero JSON: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 2. Insertar los datos en la Base de Datos mediante JDBC (con Transacción)
        String sql = "INSERT INTO usuarios (nombre, clave) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            conn.setAutoCommit(false); // Obligatorio para control de transacciones

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Usuario u : listaUsuarios) {
                    pstmt.setString(1, u.getNombre());
                    pstmt.setString(2, u.getClave());
                    pstmt.executeUpdate();
                }

                conn.commit(); // Confirmar cambios si todo va bien
                System.out.println("¡Usuarios importados desde JSON a la BD con éxito!");

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
        importarUsuariosDesdeJson("usuarios.json");
    }
}
