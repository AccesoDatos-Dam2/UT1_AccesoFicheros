package lecturaEscrituraALL.ficheroobjeto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestSerializacion {
	public static void main(String[] args) {
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado(1, "Carlos"));
        empleados.add(new Empleado(2, "Sofía"));

        // Serializar (Guardar lista de objetos)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("empleados.dat"))) {
            oos.writeObject(empleados);
            System.out.println("Empleados serializados con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserializar (Leer lista de objetos)
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("empleados.dat"))) {
            List<Empleado> listaLeida = (List<Empleado>) ois.readObject();
            for (Empleado emp : listaLeida) {
                System.out.println(emp);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
