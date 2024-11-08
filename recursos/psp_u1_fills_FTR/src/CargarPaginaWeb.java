import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CargarPaginaWeb {
    public static void main(String[] args) {
        //No compruebo los argumentos, porque ya lo hace el padre
        String urlString = args[0];
        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// conecta a la pagina
            connection.setRequestMethod("GET");//GET es el metodo mas comun

            int responseCode = connection.getResponseCode();//Cogo el codigo de respuesta
            if (responseCode != 200) {//200 es el codigo que da cuando todo a ido bien
                System.out.println("Error al conectar: Código de respuesta " + responseCode);
                System.exit(2);
            }

            // Leo y envio al padre el contenido de la página
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                String linea;

                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}