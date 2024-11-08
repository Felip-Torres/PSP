import java.io.*;

public class CrearArchivoIndex {
    public static void main(String[] args) {
        File encryptedFile = new File("encrypted.txt");
        //No hace falta que compruebe si existe porque ya lo hace el padre

        File indexFile = new File("index.html");

        try (BufferedReader reader = new BufferedReader(new FileReader(encryptedFile));
             PrintWriter writer = new PrintWriter(new FileWriter(indexFile))) {

            StringBuilder contenidoBody = new StringBuilder();
            String linea;
            //Booleano que dira si tengo que copiar la linea o no
            boolean dentroBody = false;

            // Leer el contenido de encrypted.txt y extraer la sección <body>
            while ((linea = reader.readLine()) != null) {
                //Si es body cogo esta linea y pongo el booleano a true
                if (linea.contains("<body")) {
                    contenidoBody.append(linea).append("\n");
                    dentroBody = true;
                } else if (linea.contains("</body>")) {//Si es el final de body pongo el booleano a false
                    dentroBody = false;
                } else if (dentroBody) {//Mientras este dentro copio la linea
                    contenidoBody.append(linea).append("\n");
                }
            }

            // Generar el archivo index.html con la estructura HTML
            writer.println("<html>");
            writer.println("<head><title>Contenido de encrypted.txt</title></head>");
            writer.println(contenidoBody.toString());
            writer.println("</body>");
            writer.println("</html>");

            //Aviso al padre de que se a generaro el index
            System.out.println("Archivo index.html creado con exito.");

        } catch (IOException e) {
            System.err.println("Error al crear index.html: " + e.getMessage());
            System.exit(2);
        }
    }
}