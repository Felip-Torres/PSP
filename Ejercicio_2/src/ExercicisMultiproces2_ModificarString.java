import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExercicisMultiproces2_ModificarString {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String read = br.readLine();
            String mod = read.toUpperCase().replaceAll("[AEIOU]", "_");
            System.out.println("Fill diu: " + mod);
        } catch (Exception e) {
            System.out.println("Fill diu: error");
        }
    }
}
