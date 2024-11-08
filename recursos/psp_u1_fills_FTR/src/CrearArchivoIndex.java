import java.io.*;

public class CrearArchivoIndex {
    public static void main(String[] args) {
        File encryptedFile = new File("encrypted.txt");
        File indexFile = new File("index.html");

        try (BufferedReader reader = new BufferedReader(new FileReader(encryptedFile));
             PrintWriter writer = new PrintWriter(new FileWriter(indexFile))) {

            // Leer el contenido de encrypted.txt y extraer la sección <body>
            StringBuilder contenidoBody = new StringBuilder();
            String linea;
            boolean dentroBody = false;

            while ((linea = reader.readLine()) != null) {
                if (linea.contains("<body")) {
                    contenidoBody.append(linea).append("\n");
                    dentroBody = true;
                } else if (linea.contains("</body>")) {
                    dentroBody = false;
                } else if (dentroBody) {
                    contenidoBody.append(linea).append("\n");
                }
            }

            // Generar el archivo index.html con la estructura HTML
            writer.println("<html>");
            writer.println("<head><title>Contenido de encrypted.txt</title></head>");
            writer.println(contenidoBody.toString().trim());
            writer.println("</body>");
            writer.println("</html>");

            System.out.println("Archivo index.html creado con exito.");

        } catch (IOException e) {
            System.err.println("Error al crear index.html: " + e.getMessage());
            System.exit(2);
        }
    }
}