package entidades.powerups;

import fabricas.Sprite;

public class FlorDeFuego extends PowerUp{

	private static final long serialVersionUID = 1L;

	public FlorDeFuego(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public ListaPowerUps get_tipo() {
		return ListaPowerUps.FLOR_DE_FUEGO;
	}

}
