package entidades.powerups;

import fabricas.Sprite;

public class SuperChampi extends PowerUp{

	private static final long serialVersionUID = 1L;

	public SuperChampi(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public ListaPowerUps get_tipo() {
		return ListaPowerUps.SUPER_CHAMPI;
	}

}
