import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

public class BuscarPalabraClave {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Se requiere un argumento");
            System.exit(1);
        }

        String palabraClave = args[0];

        // Leer el contenido HTML desde System.in
        StringBuilder contenidoHTML = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenidoHTML.append(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el contenido: " + e.getMessage());
            System.exit(3);
        }

        // Buscar la palabra clave en el contenido HTML
        // \b son los espacios y puntos, si no los pusiera tambien incluiria en la busqueda palabras que contengan la clave
        Pattern pattern = Pattern.compile("\\b" + palabraClave + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(contenidoHTML.toString());

        if (matcher.find()) {
            System.out.println("La palabra clave '" + palabraClave + "' se encontro");
        } else {
            System.out.println("La palabra clave '" + palabraClave + "' no se encontro");
        }
    }
}
