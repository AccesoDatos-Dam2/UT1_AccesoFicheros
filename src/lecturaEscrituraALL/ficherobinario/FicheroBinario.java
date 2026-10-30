package lecturaEscrituraALL.ficherobinario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FicheroBinario {
	public static void main(String[] args) {
        // Escritura binaria
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("datos.bin"))) {
            dos.writeInt(10);
            dos.writeDouble(99.99);
            dos.writeUTF("Acceso a Datos");
            System.out.println("Datos binarios escritos.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lectura binaria (Ojo: se lee en el mismo orden que se escribió)
        try (DataInputStream dis = new DataInputStream(new FileInputStream("datos.bin"))) {
            int entero = dis.readInt();
            double decimal = dis.readDouble();
            String texto = dis.readUTF();

            System.out.println("Leído -> Entero: " + entero + ", Decimal: " + decimal + ", Texto: " + texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
