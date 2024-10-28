package entidades.plataformas;

import entidades.Entidad;
import fabricas.Sprite;

@SuppressWarnings("serial") 
public abstract class Plataforma extends Entidad {
	
	public Plataforma(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

}
