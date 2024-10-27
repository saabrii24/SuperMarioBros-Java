package fabricas;

/**
 * La clase {@code Dominio1Factory} es una fábrica concreta que extiende la clase
 * {@code SpritesFactory} y se utiliza para crear sprites específicos del dominio 1.
 * Esta fábrica carga imágenes que representan a Mario.
 */
public class Dominio1Factory extends SpritesFactory {

    public Dominio1Factory() {
        super("/assets/imagenes/sprites/dominio1/mario_");
    }

    @Override
    public String toString() {
        return "Mario";
    }
}
