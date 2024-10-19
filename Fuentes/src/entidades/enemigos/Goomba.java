package entidades.enemigos;

import fabricas.Sprite;
import logica.Mapa;

public class Goomba extends Enemigo{
	public Goomba(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		set_velocidad_en_x(3);
	}

}
