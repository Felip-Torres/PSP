import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

public class BuscarPalabraClave {
    public static void main(String[] args) {
        //No compruebo los argumentos, porque ya lo hace el padre
        String palabraClave = args[0];

        //Leo el textoHTML del padre
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

        // Busco la palabra clave en el contenido HTML
        // \b delimitan el inicio y final de una palabra, si no los pusiera tambien incluiria en la busqueda palabras que contengan la clave
        Pattern pattern = Pattern.compile("\\b" + palabraClave + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(contenidoHTML.toString());

        //Find recorre el string hasta encontrar el patron, devuelve false si no lo encuentra
        if (matcher.find()) {
            System.out.println("La palabra clave '" + palabraClave + "' se encontro");
        } else {
            System.out.println("La palabra clave '" + palabraClave + "' no se encontro");
        }
    }
}
