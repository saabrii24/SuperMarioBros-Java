package entidades.powerups;

import entidades.Entidad;
import entidades.mario.Mario;
import fabricas.Sprite;

public abstract class PowerUp extends Entidad{

	private static final long serialVersionUID = 1L;
	
	public PowerUp(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public abstract ListaPowerUps get_tipo();
	public abstract void aplicar_efecto(Mario mario);
}
