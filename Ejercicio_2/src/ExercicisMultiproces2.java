import java.io.*;
import java.util.Scanner;

public class ExercicisMultiproces2 {

    static final String dirPath="C:\\Users\\Alumne\\Dropbox\\com\\segundo\\PSP\\Ejercicio_2\\src\\";
    static final String[] command = {
            "java",
            "ExercicisMultiproces2_ModificarString.java"
    };
    private static Process execPrograma() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(new File(dirPath));
        pb.redirectError(new File("error.txt"));
        return pb.start();
    }

    private static void enviar(Process p, String n) throws IOException {
        OutputStream os = p.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter outBR = new BufferedWriter(osw);

        outBR.write(n);
        outBR.newLine();
        outBR.flush();

        outBR.close();
    }

    private static String llegir(Process p) throws IOException {
        InputStream is = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        return br.readLine();
    }

    public static void main(String[] args) throws IOException {
        Process process= execPrograma();
        String cad="";
        for (String arg : args) {
            cad+=arg+" ";
        }
        System.out.println(cad);
        enviar(process, cad);
        String retorn=llegir(process);
        System.out.println("Pare diu: "+retorn);
    }
}