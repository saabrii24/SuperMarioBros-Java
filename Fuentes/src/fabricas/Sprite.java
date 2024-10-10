package fabricas;

import javax.swing.ImageIcon;

/**
 * La clase {@code Sprite} representa una imagen que se utiliza en el juego.
 * Proporciona métodos para obtener la ruta de la imagen, así como su ancho y alto.
 */
public class Sprite {

    /**
     * Ruta al archivo de imagen utilizado para el sprite.
     */
    protected String ruta_a_imagen;

    /**
     * Ancho de la imagen en píxeles.
     */
    protected int ancho;

    /**
     * Alto de la imagen en píxeles.
     */
    protected int alto;

    /**
     * Crea una instancia de {@code Sprite} con la ruta especificada a la imagen.
     * Se carga la imagen y se calculan su ancho y alto.
     *
     * @param ruta_a_imagen La ruta al archivo de imagen utilizado para el sprite.
     */
    public Sprite(String ruta_a_imagen) {
        this.ruta_a_imagen = ruta_a_imagen;
        ImageIcon imagen = new ImageIcon(ruta_a_imagen);
        this.ancho = imagen.getIconWidth();
        this.alto = imagen.getIconHeight();
    }

    /**
     * Obtiene la ruta de la imagen utilizada por el sprite.
     *
     * @return La ruta al archivo de imagen.
     */
    public String get_ruta_imagen() {
        return ruta_a_imagen;
    }

    /**
     * Obtiene el ancho de la imagen del sprite en píxeles.
     *
     * @return El ancho de la imagen en píxeles.
     */
    public int get_ancho() {
        return ancho;
    }

    /**
     * Obtiene el alto de la imagen del sprite en píxeles.
     *
     * @return El alto de la imagen en píxeles.
     */
    public int get_alto() {
        return alto;
    }
}
