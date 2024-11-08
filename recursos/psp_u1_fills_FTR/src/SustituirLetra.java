import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SustituirLetra {
    public static void main(String[] args) {
        //No compruebo los argumentos, porque ya lo hace el padre
        String letraOriginal = args[0];
        String letraSustituta = args[1];

        StringBuilder contenidoModificado = new StringBuilder();

        //Leo el textoHTML del padre
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenidoModificado.append(linea.replace(letraOriginal, letraSustituta)).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el contenido: " + e.getMessage());
            System.exit(2);
        }

        // Guardo el contenido modificado en "encrypted.txt"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("encrypted.txt"))) {
            writer.write(contenidoModificado.toString());
            System.out.println("Archivo 'encrypted.txt' creado con exito.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            System.exit(3);
        }
    }
}