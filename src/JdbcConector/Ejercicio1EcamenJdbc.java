package JdbcConector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio1EcamenJdbc {
	private static final String URL = "jdbc:mysql://localhost:3306/classicmodels";
    private static final String USER = "root";
    private static final String PASS = "abc123";

    public static void moverEmpleados(String oficinaOrigen, String oficinaDestino) {
        String sql = "UPDATE employees SET officeCode = ? WHERE officeCode = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            
            // 1. Desactivar el autocommit para iniciar la transacción obligatoria
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, oficinaDestino);
                pstmt.setString(2, oficinaOrigen);

                // Ejecutar la actualización y capturar el número de filas modificadas
                int filasAfectadas = pstmt.executeUpdate();

                // Validación: si no se actualizó ningún empleado, la oficina origen no tenía personal
                if (filasAfectadas == 0) {
                    System.out.println("Aviso: No hay empleados en la oficina de origen (" + oficinaOrigen + ") para mover.");
                    conn.rollback(); // Opcional pero recomendado para mantener la consistencia
                    return;
                }

                // 2. Si todo va bien, confirmar la transacción (Commit)
                conn.commit();
                System.out.println("¡Transacción exitosa! Se han movido " + filasAfectadas + 
                                   " empleados de la oficina " + oficinaOrigen + " a la oficina " + oficinaDestino + ".");

            } catch (SQLException e) {
                // Si ocurre cualquier fallo dentro del bloque, deshacemos los cambios (Rollback)
                conn.rollback();
                System.err.println("Error durante la actualización. Ejecutando Rollback...");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Prueba de ejemplo: mover los empleados de la oficina '4' a la oficina '1'
        moverEmpleados("4", "1");
    }
}
