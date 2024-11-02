import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Principal {
    private static String paginaWeb = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar la URL y validar el formato
        while (true) {
            System.out.print("Introduce la URL de la página web (debe comenzar con http:// o https://): ");
            String Url = scanner.nextLine();

            if (Url.startsWith("http://") || Url.startsWith("https://")) {
                paginaWeb = Url;
                System.out.println("URL válida.");
                break;
            } else {
                System.out.println("URL no válida.");
            }
        }

        // Menú principal
        int opcion;
        do {
            System.out.println("""
            \nMenú principal:
            1. Cargar página Web
            2. Analizar el número de caracteres
            3. Sustituir letra
            4. Leer encrypted.txt
            5. Buscar palabras clave
            6. Crear archivo index.html
            7. Ejecutar archivo index.html
            8. Salir
            """);
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            // Ejecutar opción seleccionada
            switch (opcion) {
                case 1 -> cargarPaginaWeb();
                case 2 -> System.out.println("Función para analizar el número de caracteres aún no implementada.");
                case 3 -> System.out.println("Función para sustituir letra aún no implementada.");
                case 4 -> System.out.println("Función para leer encrypted.txt aún no implementada.");
                case 5 -> System.out.println("Función para buscar palabras clave aún no implementada.");
                case 6 -> System.out.println("Función para crear archivo index.html aún no implementada.");
                case 7 -> System.out.println("Función para ejecutar archivo index.html aún no implementada.");
                case 8 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 8);

        scanner.close();
    }

    // Método para cargar la página web (Implementación pendiente)
    private static void cargarPaginaWeb() {
        String relativePath = "../psp_u1_fills_FTR/out/production/psp_u1_fills_FTR";
        String errorLogPath = "../psp_u1_pare_FTR/errores.txt";

        // Obtener el directorio actual
        String currentDir = System.getProperty("user.dir");
        String com = Paths.get(currentDir, relativePath).toString();
        String errorLog = Paths.get(currentDir, errorLogPath).toString();
        String filename="CargarPaginaWeb";
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java",filename,paginaWeb);
            pb.directory(new File(com));
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(new File(errorLog));
            Process p=pb.start();

            // Leer la salida del proceso
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                System.out.println("Contenido de la página web:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                System.out.println("Error al cargar la página web. Código de salida: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error al intentar cargar la página web: " + e.getMessage());
        }
    }
}