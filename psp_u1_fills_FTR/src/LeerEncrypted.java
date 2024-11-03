import java.io.*;

public class LeerEncrypted {
    public static void main(String[] args) {
        File archivo = new File("encrypted.txt");

        // Comprobar si el archivo existe
        if (!archivo.exists()) {
            System.err.println("El archivo encrypted.txt no existe.");
            System.exit(1);
        }

        // Leer el contenido del archivo y enviarlo al proceso padre
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea); // Envia cada línea al proceso padre
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            System.exit(2); // Código de error 2
        }
    }
}
