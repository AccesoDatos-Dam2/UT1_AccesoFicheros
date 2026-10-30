package combinaciones.jbdcJson;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JbdcToJson {
	// Constantes de conexión a la Base de Datos (URL, Usuario y Contraseña)
    private static final String URL = "jdbc:mysql://localhost:3306/classicmodels";
    private static final String USER = "root";
    private static final String PASS = "dbpassword";

    // Método principal que recibe el código de oficina y la ruta del JSON de destino
    public static void exportarEmpleadosPorOficina(String officeCode, String rutaJson) {
        
        // Consulta SQL parametrizada para evitar inyecciones SQL
        String sql = "SELECT firstName, lastName, email FROM employees WHERE officeCode = ?";
        
        // Lista dinámica para almacenar los objetos Empleado obtenidos de la BD
        List<Empleado> listaEmpleados = new ArrayList<>();

        // 1. Ejecutar consulta JDBC usando try-with-resources para asegurar el cierre automático de conexiones
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Asignar el valor del parámetro (officeCode) a la interrogación (?) de la consulta
            pstmt.setString(1, officeCode);

            // Ejecutar la consulta y recorrer el ResultSet resultante
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Extraer los campos fila por fila de la base de datos
                    String nombre = rs.getString("firstName");
                    String apellido = rs.getString("lastName");
                    String email = rs.getString("email");

                    // Crear el objeto Empleado con los 3 parámetros y añadirlo a la lista
                    listaEmpleados.add(new Empleado(nombre, apellido, email));
                }
            }

        } catch (SQLException e) {
            // Control de errores específicos de JDBC / Base de Datos
            System.err.println("Error en la base de datos: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 2. Exportar la lista de objetos Java a un fichero físico en formato JSON usando Gson
        // GsonBuilder().setPrettyPrinting() se usa para tabular y dar formato legible al JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try (Writer writer = new FileWriter(rutaJson)) {
            // Serializa la lista completa de empleados volcándola en el flujo de escritura del fichero
            gson.toJson(listaEmpleados, writer);
            System.out.println("¡Empleados exportados correctamente a JSON en: " + rutaJson + "!");
        } catch (IOException e) {
            // Control de errores de entrada/salida (E/S) al escribir el fichero
            System.err.println("Error al escribir el fichero JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método main para realizar pruebas locales de ejecución
    public static void main(String[] args) {
        exportarEmpleadosPorOficina("1", "empleados_oficina1.json");
    }
}
