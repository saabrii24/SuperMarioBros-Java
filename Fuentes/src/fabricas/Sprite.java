package fabricas;

import java.awt.Image;
import java.awt.Toolkit;

public class Sprite {

    protected String ruta_a_imagen;
    protected int ancho;
    protected int alto;
    protected Image imagen;

    public Sprite(String ruta_a_imagen) {
        this.ruta_a_imagen = ruta_a_imagen;
        this.imagen = Toolkit.getDefaultToolkit().getImage(ruta_a_imagen);
        this.ancho = imagen.getWidth(null);
        this.alto = imagen.getHeight(null);
        
    }

    public Image get_imagen_escalada(int nuevo_ancho, int nuevo_alto) {
        return imagen.getScaledInstance(nuevo_ancho, nuevo_alto, Image.SCALE_SMOOTH);
    }

    public String get_ruta_imagen() {
        return ruta_a_imagen;
    }

    public int get_ancho() {
        return ancho;
    }

    public int get_alto() {
        return alto;
    }

	public void set_alto(int nuevo_alto) {
		alto = nuevo_alto;
	}

	public void set_ancho(int nuevo_ancho) {
		ancho = nuevo_ancho;
	}
}