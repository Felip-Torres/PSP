import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class Fill {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Recibir el mensaje del padre
            String mensaje = br.readLine();
            System.out.println("\tFill: rep missatge del pare \"" + mensaje + "\"");

            // Processar el mensaje y enviar la respuesta
            mensaje = "Salutacions de part del fill";
            System.out.println("\tFill: envia missatge del pare");


            OutputStream os = System.out;
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            // Enviar la respuesta al padre
            bw.write(mensaje);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

