package fabricas;

/**
 * La clase {@code Dominio2Factory} es una fábrica concreta que extiende la clase
 * {@code SpritesFactory} y se utiliza para crear sprites específicos del dominio 2.
 * Esta fábrica carga imágenes que representan a Luigi.
 */
public class Dominio2Factory extends SpritesFactory {

    /**
     * Crea una instancia de {@code Dominio2Factory} que inicializa la ruta base de las
     * imágenes del dominio 2 utilizadas para los sprites.
     */
    public Dominio2Factory() {
        super("/assets/imagenes/sprites/dominio2/luigi_");
    }

    /**
     * Devuelve una representación en cadena del nombre de los personajes del dominio 2.
     *
     * @return El nombre del personaje del dominio 2 como {@code String}, en este caso, "Luigi".
     */
    @Override
    public String toString() {
        return "Luigi";
    }
}
