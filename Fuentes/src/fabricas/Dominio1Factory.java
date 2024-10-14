package fabricas;

/**
 * La clase {@code Dominio1Factory} es una fábrica concreta que extiende la clase
 * {@code SpritesFactory} y se utiliza para crear sprites específicos del dominio 1.
 * Esta fábrica carga imágenes que representan a Mario.
 */
public class Dominio1Factory extends SpritesFactory {

    /**
     * Crea una instancia de {@code Dominio1Factory} que inicializa la ruta base de las
     * imágenes del dominio 1 utilizadas para los sprites.
     */
    public Dominio1Factory() {
        super("/assets/imagenes/sprites/dominio1/mario_");
    }

    /**
     * Devuelve una representación en cadena del nombre de los personajes del dominio 1.
     *
     * @return El nombre del personaje del dominio 1 como {@code String}, en este caso, "Mario".
     */
    @Override
    public String toString() {
        return "Mario";
    }
}
