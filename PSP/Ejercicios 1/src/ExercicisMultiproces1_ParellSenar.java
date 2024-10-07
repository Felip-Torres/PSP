public class ExercicisMultiproces1_ParellSenar {
    public void ParellSenar(int n) {
        if(n%2 == 0){
            System.out.println("Parell");
        }else {
            System.out.println("Senar");
        }
    }
    public static void main(String[] args) {
        ExercicisMultiproces1_ParellSenar p = new ExercicisMultiproces1_ParellSenar();
        int n = Integer.parseInt(args[0]);

        p.ParellSenar(n);
    }
}
