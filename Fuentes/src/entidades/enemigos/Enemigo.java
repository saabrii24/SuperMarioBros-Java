package entidades.enemigos;

import entidades.EntidadMovible;
import fabricas.Sprite;
import logica.Mapa;

public abstract class Enemigo extends EntidadMovible{
	
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
