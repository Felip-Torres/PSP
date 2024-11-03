import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Principal {
    private static String paginaWeb = "";
    private static String textoHTML="";
    private static final String relativePath = "../psp_u1_fills_FTR/out/production/psp_u1_fills_FTR";
    private static final String errorLogPath = "../psp_u1_pare_FTR/errores.txt";


    public static void main(String[] args) {
        // Solicitar la URL y validar el formato
        while (true) {
            System.out.print("Introduce la URL de la página web (debe comenzar con http:// o https://): ");
            //String Url = scanner.nextLine();
            String Url = "https://paucasesnovescifp.cat/";


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
        Scanner sc = new Scanner(System.in);
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
            opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer

            // Ejecutar opción seleccionada
            switch (opcion) {
                case 1 -> cargarPaginaWeb();
                case 2 -> analizarCaracter();
                case 3 -> sustituirLetra();
                case 4 -> System.out.println("Función para leer encrypted.txt aún no implementada.");
                case 5 -> System.out.println("Función para buscar palabras clave aún no implementada.");
                case 6 -> System.out.println("Función para crear archivo index.html aún no implementada.");
                case 7 -> System.out.println("Función para ejecutar archivo index.html aún no implementada.");
                case 8 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }

        } while (opcion != 8);
        sc.close();

    }

    // Método para cargar la página web
    private static void cargarPaginaWeb() {
        // Obtener el directorio actual
        String currentDir = System.getProperty("user.dir");
        String com = Paths.get(currentDir, relativePath).toString();
        String errorLog = Paths.get(currentDir, errorLogPath).toString();
        String filename="CargarPaginaWeb";
        ProcessBuilder pb;

        try {
            pb = new ProcessBuilder("java",filename,paginaWeb);
            pb.directory(new File(com));
            pb.redirectError(new File(errorLog));
            Process p=pb.start();

            // Leer la salida del proceso y almacenar en textoHTML
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                System.out.println("Contenido de la página web:");
                while ((linea = reader.readLine()) != null) {
                    sb.append(linea);
                }
                textoHTML = sb.toString();
            }
            p.waitFor();

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(textoHTML);
    }

    public static void analizarCaracter(){
        if(textoHTML.isEmpty()) {
            System.out.println("Primero se necesita cargar la pagina web. Este proceso se hara automaticamente");
            cargarPaginaWeb();
        }

        Scanner sc = new Scanner(System.in);
        String input;

        do {
            // Pedir al usuario el carácter a contar
            System.out.print("\nIntroduce el carácter que deseas contar: ");
            input = sc.nextLine();

            // Validar que solo se ha introducido un carácter
            if (input.length() != 1) {
                System.out.println("Debes introducir un solo carácter.");
            }
        }while (input.length() != 1);

        // Obtener el directorio actual
        String currentDir = System.getProperty("user.dir");
        String com = Paths.get(currentDir, relativePath).toString();
        String errorLog = Paths.get(currentDir, errorLogPath).toString();
        String filename="AnalizarNombreCaracteres";
        ProcessBuilder pb;

        try {
            pb = new ProcessBuilder("java",filename, input);
            pb.directory(new File(com));
            pb.redirectError(new File(errorLog));
            Process p=pb.start();

            // Enviar el contenido de textoHTML al proceso hijo a través de la salida estándar
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                writer.write(textoHTML);
            }

            // Leer la salida del proceso hijo
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String result = reader.readLine();
                System.out.println("El carácter '" + input + "' aparece " + result + " veces.");
            }
            p.waitFor();

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sustituirLetra() {
        if (textoHTML.equals("")) {
            System.out.println("Primero se necesita cargar la página web. Este proceso se hará automáticamente.");
            cargarPaginaWeb();
        }

        Scanner sc = new Scanner(System.in);
        String letraOriginal;
        String letraSustituta;

        // Validar que el usuario introduzca solo un carácter para cada letra
        do {
            System.out.print("Introduce la letra que sustituir: ");
            letraOriginal = sc.nextLine();
            if (letraOriginal.length() != 1 || !Character.isLetter(letraOriginal.charAt(0))) {
                System.out.println("Debes introducir solo una letra.");
            }
        } while (letraOriginal.length() != 1 || !Character.isLetter(letraOriginal.charAt(0)));

        do {
            System.out.print("Introduce la letra que sustituira '" + letraOriginal + "': ");
            letraSustituta = sc.nextLine();
            if (letraSustituta.length() != 1 || !Character.isLetter(letraSustituta.charAt(0))) {
                System.out.println("Debes introducir solo una letra.");
            }
        } while (letraSustituta.length() != 1 || !Character.isLetter(letraSustituta.charAt(0)));

        // Obtener el directorio actual
        String currentDir = System.getProperty("user.dir");
        String com = Paths.get(currentDir, relativePath).toString();
        String errorLog = Paths.get(currentDir, errorLogPath).toString();
        String filename="SustituirLetra";
        ProcessBuilder pb;

        try {
            pb = new ProcessBuilder("java",filename, letraOriginal, letraSustituta);
            pb.directory(new File(com));
            pb.redirectError(new File(errorLog));
            Process p=pb.start();

            // Enviar el contenido de textoHTML al proceso hijo
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                writer.write(textoHTML);
            }

            // Leer la respuesta del proceso hijo
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String mensaje = reader.readLine();
                System.out.println(mensaje);
            }
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    public static void leerEncrypted() {
        // Directorio del archivo LeerEncrypted.java
        String currentDir = System.getProperty("user.dir");
        String com = Paths.get(currentDir, relativePath).toString();
        String errorLog = Paths.get(currentDir, errorLogPath).toString();
        String filename = "LeerEncrypted";

        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", filename);
            pb.directory(new File(com));
            pb.redirectError(new File(errorLog));
            Process p = pb.start();

            // Leer la salida del proceso hijo
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea); // Mostrar el contenido de encrypted.txt
                }
            }

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                System.out.println("Error al leer encrypted.txt. Código de salida: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}