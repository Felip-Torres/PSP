import java.io.*;
import java.util.Scanner;

public class Principal {
    //Guardara la url de la pagina web
    private static String Url = "";

    //Guardara el html de la pagina web
    private static String textoHTML="";

    //Rutas relativas
    private static final String com = "../psp_u1_fills_FTR/";
    private static final String errorLog = "errores.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Solicitar la URL y validar el formato
        do {
            System.out.print("Introduce la URL de la página web (debe comenzar con http:// o https://): ");
            Url = sc.nextLine();//Para no escribir la URL cada vez comenta esta linea y descomenta la siguiente
            //Url = "https://paucasesnovescifp.cat/";//Utilizo esta linea para probar el programa

            //Compruebo que la url es correcta
            if (Url.startsWith("http://") || Url.startsWith("https://")) {
                System.out.println("URL válida.");
            } else {
                System.out.println("URL no válida.");
            }
        }while (!Url.startsWith("http://") && !Url.startsWith("https://"));
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
        //Como he usado waitfor en todos los metodos, todos los processos deberian estar cerrados, por lo que solo falta cerrar el scanner
        sc.close();

    }

    // Método para cargar la página web
    private static void cargarPaginaWeb() {
        //Nombre del proceso hijo
        String filename="CargarPaginaWeb";

        ProcessBuilder pb;
        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar",filename,Url);
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
        //Compruebo si se ha cargado la pagina en textoHTML
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

        //Nombre del proceso hijo
        String filename="AnalizarNombreCaracteres";
        ProcessBuilder pb;

        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar",filename, input);
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
        //Compruebo si se ha cargado la pagina en textoHTML
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

        //Nombre del proceso hijo
        String filename="SustituirLetra";
        ProcessBuilder pb;

        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar",filename, letraOriginal, letraSustituta);
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

        //Nombre del proceso hijo
        String filename = "LeerEncrypted";

        ProcessBuilder pb;
        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar", filename);
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
        //Compruebo si se ha cargado la pagina en textoHTML
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

        //Nombre del proceso hijo
        String filename = "BuscarPalabraClave";
        ProcessBuilder pb;

        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar", filename, palabraClave);
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
            String respuesta = sc.nextLine().trim().toLowerCase();//uso trim para eleminar posibles epacios y lower case para la mayusculas

            //Si la respuesta es s ejecuto el metodo 3
            if (respuesta.equals("s")) {
                sustituirLetra();
            } else {
                System.out.println("No se puede crear index.html sin el archivo encrypted.txt.");
                return;
            }
        }

        System.out.println("Creando index:");

        //Nombre del proceso hijo
        String filename = "CrearArchivoIndex";

        ProcessBuilder pb;
        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar", filename);
            pb.directory(new File(com));
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

        //Nombre del proceso hijo
        String filename = "EjecutarIndex";

        ProcessBuilder pb;
        try {
            //Creo el processbuilder y el processo
            pb = new ProcessBuilder("java","-cp","psp_u1_fills_FTR.jar", filename);
            pb.directory(new File(com));
            pb.redirectError(new File(errorLog));
            Process p = pb.start();
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}

