import java.io.*;

public class LeerEncrypted {
    public static void main(String[] args) {
        File archivo = new File("encrypted.txt");
        //No hace falta que compruebe si existe porque ya lo hace el padre

        // Leer el contenido del archivo y enviarlo al proceso padre
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            System.exit(1);
        }
    }
}
