import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EjecutarIndex {
    public static void main(String[] args) {
        File index = new File("index.html");
        //No hace falta que compruebe si existe porque ya lo hace el padre

        // Verifico si Desktop es compatible en el sistema actual
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            //Verifico si el navegador es compatible
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(index.toURI());//Abro el navegador con el index
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("index.html se ha abierto en el navegador.");
            } else {
                System.out.println("La función de navegador no es compatible en este sistema.");
            }
        } else {
            System.out.println("La función Desktop no es compatible en este sistema.");
        }
    }
}
