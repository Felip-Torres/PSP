import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EjecutarIndex {
    public static void main(String[] args) {
        File index = new File("index.html");

        // Verifica si Desktop es compatible en el sistema actual
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(index.toURI());
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
