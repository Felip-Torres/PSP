import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExercicisMultiproces1 {

    public static void ParellSenar(){
        Scanner sc = new Scanner(System.in);

        int n=-1;
        String linea="";

        String com="C:\\Users\\Alumne\\Dropbox\\com\\segundo\\PSP\\Ejercicios 1\\src\\ExercicisMultiproces1_ParellSenar.java";
        ProcessBuilder pb;

        do {
            try {
                System.out.println("Ingrese el numero: ");
                n = sc.nextInt();
                pb = new ProcessBuilder("java",com,String.valueOf(n));
                pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                pb.redirectError(new File("C:\\Users\\Alumne\\Dropbox\\com\\segundo\\PSP\\Ejercicios 1\\errores.txt"));
                pb.start();
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido");
            }catch (InputMismatchException e) {
                linea=sc.nextLine();
                if (!linea.equals("exit"))System.out.println("Escribe exit para salir");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (linea.equals("exit")){
                    n=-1;
                }
            }
        }while (n!=-1);

    }
    public static void main(String[] args) {
        ParellSenar();
    }
}