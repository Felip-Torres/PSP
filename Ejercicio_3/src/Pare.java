import java.io.*;

public class Pare {
    static final String dirPath="C:\\Users\\Alumne\\Dropbox\\com\\segundo\\PSP\\Ejercicio_3\\src\\";
    static final String[] command = {
            "java", "Fill.java"  // Executa el programa fill
    };

    private static Process execFill() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(new File(dirPath));
        pb.redirectError(new File("error.txt"));  // Redirigeix els errors a un fitxer
        return pb.start();  // Inicia el procés fill
    }

    private static void enviarMissatge(Process p, String missatge) throws IOException {
        OutputStream os = p.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        // Envia missatge al fill
        bw.write(missatge);
        bw.newLine();
        bw.flush();

        bw.close();
    }

    private static void rebreMissatge(Process p) throws IOException {
        InputStream is = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        // Leer el mensage del hijo
        String linia;
        while ((linia = br.readLine()) != null) {
            if(linia.startsWith("\t")){
                System.out.println(linia);
            }else {
                System.out.println("Pare: rep missatge del fill \"" + linia + "\"");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // Iniciar el proceso hijo
        Process fill = execFill();

        // Enviar mensaje al hijo
        String missatge = "Salutacions del pare";
        System.out.println("Pare: envia missatge");
        enviarMissatge(fill, missatge);

        // Recibir mensaje del hijo
        rebreMissatge(fill);

        // Esperar que el processo hijo acabe
        try {
            fill.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

