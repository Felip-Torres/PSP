import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CargarPaginaWeb {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Argumentos incorrectos");
            System.exit(1);
        }

        String urlString = args[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Verificar respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error al conectar: Código de respuesta " + responseCode);
                System.exit(2);
            }

            // Leer el contenido de la página
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al descargar la página: " + e.getMessage());
        }
    }
}