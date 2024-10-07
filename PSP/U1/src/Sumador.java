public class Sumador {
    public int suma(int a, int b) {
        int result = 0;
        for (int i = a; i <= b; i++) {
            result += i;
        }
        return result;
    }

    public static void main(String[] args) {
        Sumador sumador = new Sumador();
        System.out.println(sumador.suma(10, 20));
    }
}
