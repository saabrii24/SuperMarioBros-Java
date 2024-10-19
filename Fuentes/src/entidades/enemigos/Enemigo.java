package entidades.enemigos;

import entidades.Entidad;
import fabricas.Sprite;
import logica.Mapa;

public class Enemigo extends Entidad{
	
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

    public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_enemigo(this);
            destruir();
        }
    }
}
