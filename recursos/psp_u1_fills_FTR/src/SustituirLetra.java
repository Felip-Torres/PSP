import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SustituirLetra {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Se requieren dos letras como argumentos.");
            System.exit(1);
        }

        String letraOriginal = args[0];
        String letraSustituta = args[1];
        StringBuilder contenidoModificado = new StringBuilder();

        // Leer el contenido enviado por el proceso padre
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenidoModificado.append(linea.replace(letraOriginal, letraSustituta)).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el contenido: " + e.getMessage());
            System.exit(2);
        }

        // Guardar el contenido modificado en "encrypted.txt"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("encrypted.txt"))) {
            writer.write(contenidoModificado.toString());
            System.out.println("Archivo 'encrypted.txt' creado con exito.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            System.exit(3);
        }
    }
}