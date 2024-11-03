import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnalizarNombreCaracteres {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Argumentos incorrectos");
            System.exit(1);
        }

        char character = args[0].charAt(0); // Carácter a contar
        int count = 0;

        StringBuilder contenidoHTML = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenidoHTML.append(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el contenido: " + e.getMessage());
            System.exit(1);
        }

        String html = contenidoHTML.toString();
        // Contar el número de veces que el carácter aparece en el HTML
        for (int i = 0; i < html.length(); i++) {
            if (html.charAt(i) == character) {
                count++;
            }
        }

        // Imprimir el número de veces que aparece el carácter
        System.out.println(count);
    }
}