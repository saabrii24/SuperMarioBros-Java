package entidades.powerups;

import entidades.Entidad;
import fabricas.Sprite;

public abstract class PowerUp extends Entidad{

	private static final long serialVersionUID = 1L;

	public PowerUp(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public abstract ListaPowerUps get_tipo();
}
