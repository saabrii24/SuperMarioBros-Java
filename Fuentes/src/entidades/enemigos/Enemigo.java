package entidades.enemigos;

import entidades.EntidadMovible;
import fabricas.Sprite;
import logica.Mapa;

public abstract class Enemigo extends EntidadMovible{
	
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

}
