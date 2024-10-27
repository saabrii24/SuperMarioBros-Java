package fabricas;

/**
 * La clase {@code Dominio2Factory} es una fábrica concreta que extiende la clase
 * {@code SpritesFactory} y se utiliza para crear sprites específicos del dominio 2.
 * Esta fábrica carga imágenes que representan a Luigi.
 */
public class Dominio2Factory extends SpritesFactory {
	
    public Dominio2Factory() {
        super("/assets/imagenes/sprites/dominio2/luigi_");
    }
    
    @Override
    public String toString() {
        return "Luigi";
    }
}
