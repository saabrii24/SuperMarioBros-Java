package entidades.powerups;

import fabricas.Sprite;

public class Estrella extends PowerUp{

	private static final long serialVersionUID = 1L;

	public Estrella(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public ListaPowerUps get_tipo() {
		return ListaPowerUps.ESTRELLA;
	}

}
