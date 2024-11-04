import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Principal {
    private static String paginaWeb = "";
    private static String textoHTML="";
    private static final String relativePath = "../psp_u1_fills_FTR/out/production/psp_u1_fills_FTR";
    private static final String errorLogPath = "../psp_u1_pare_FTR/errores.txt";
    private static final String currentDir = System.getProperty("user.dir");
    private static final String com = Paths.get(currentDir, relativePath).toString();
    private static final String errorLog = Paths.get(currentDir, errorLogPath).toString();


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

            // Bucle para asegurar que se ingresa un número
            while (!sc.hasNextInt()) {
                System.out.println("Introduce un numero.");
                sc.nextLine(); // Limpiar la entrada no válida
            }

            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer

            // Ejecutar opción seleccionada
            switch (opcion) {
                case 1 -> cargarPaginaWeb();
                case 2 -> analizarCaracter();
                case 3 -> sustituirLetra();
                case 4 -> leerEncrypted();
                case 5 -> buscarPalabraClave();
                case 6 -> crearArchivoIndex();
                case 7 -> ejecutarArchivoIndex();
                case 8 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }

        } while (opcion != 8);
        sc.close();

    }

    // Método para cargar la página web
    private static void cargarPaginaWeb() {
        // Obtener el directorio actual
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
                    sb.append(linea).append("\n");
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
        if (textoHTML.isEmpty()) {
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
                System.out.println("Contenido de encrypted.txt");
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

    public static void buscarPalabraClave() {
        if (textoHTML.isEmpty()) {
            System.out.println("Primero se necesita cargar la página web. Este proceso se hará automáticamente.");
            cargarPaginaWeb();
        }

        Scanner sc = new Scanner(System.in);
        String palabraClave;

        // Solicitar la palabra clave
        do {
            System.out.print("Introduce la palabra clave que deseas buscar: ");
            palabraClave = sc.nextLine();

            // Validar que sea una única palabra
            if (!palabraClave.matches("\\w+")) {// \w es una expresión regular que representa cualquier carácter alfanumérico o guion bajo. Con el + le indico que haber como minimo uno pero pueden haber mas
                System.out.println("Debes introducir una única palabra válida.");
            }
        } while (!palabraClave.matches("\\w+"));

        // Directorio y archivo de errores
        String filename = "BuscarPalabraClave";
        ProcessBuilder pb;

        try {
            pb = new ProcessBuilder("java", filename, palabraClave);
            pb.directory(new File(com));
            pb.redirectError(new File(errorLog));
            Process p = pb.start();

            // Enviar contenido HTML al proceso hijo
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                writer.write(textoHTML);
                writer.flush();
            }

            // Leer la salida del proceso hijo
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String result;
                while ((result = reader.readLine()) != null) {
                    System.out.println(result);
                }
            }

            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    public static void crearArchivoIndex() {

        File encryptedFile = new File(com+"/encrypted.txt");

        // Comprobar si el archivo encrypted.txt existe
        if (!encryptedFile.exists()) {
            System.out.println("El archivo encrypted.txt no existe.");
            System.out.println("¿Deseas ejecutar la opción 3 (Sustituir letra) para crearlo? (s/n)");

            Scanner sc = new Scanner(System.in);
            String respuesta = sc.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                sustituirLetra();  // Llamar al método para sustituir letras y crear encrypted.txt
            } else {
                System.out.println("No se puede crear index.html sin el archivo encrypted.txt.");
                return;
            }
        }

        // Ejecutar el proceso hijo para crear index.html
        System.out.println("Creando index:");
        String filename = "CrearArchivoIndex";

        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", filename);
            pb.directory(new File(com));
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(new File(errorLog));
            Process p = pb.start();
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

    public static void ejecutarArchivoIndex() {

        File html = new File(com+"/index.html");

        // Comprobar si el archivo encrypted.txt existe
        if (!html.exists()) {
            System.out.println("El archivo index.html no existe.");
            System.out.println("¿Deseas ejecutar la opción 6 (Crear archivo index.html) para crearlo? (s/n)");

            Scanner sc = new Scanner(System.in);
            String respuesta = sc.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                crearArchivoIndex();
            } else {
                System.out.println("No se puede leer index.html si no existe");
                return;
            }
        }

        System.out.println("Ejecutando index:");
        String filename = "EjecutarIndex";

        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", filename);
            pb.directory(new File(com));
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(new File(errorLog));
            Process p = pb.start();
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }7
}

