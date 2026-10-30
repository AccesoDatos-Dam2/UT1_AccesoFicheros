package lecturaEscrituraALL.ficheroJDCB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJDBC {
	public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tu_base_de_datos";
        String user = "root";
        String pass = "";

        String sql = "SELECT id, nombre, email FROM clientes WHERE id > ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Asignar parámetros al PreparedStatement (Evita inyección SQL)
            pstmt.setInt(1, 0);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");
                    
                    System.out.println("Cliente [ID=" + id + ", Nombre=" + nombre + ", Email=" + email + "]");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en JDBC: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
