package entidades.powerups;

import fabricas.Sprite;

public class ChampiVerde extends PowerUp{

	private static final long serialVersionUID = 1L;

	public ChampiVerde(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public ListaPowerUps get_tipo() {
		return ListaPowerUps.CHAMPI_VERDE;
	}

}
