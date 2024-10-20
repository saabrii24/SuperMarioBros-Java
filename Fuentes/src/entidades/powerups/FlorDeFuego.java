package entidades.powerups;

import fabricas.Sprite;

public class FlorDeFuego extends PowerUp{

	private static final long serialVersionUID = 1L;

	public FlorDeFuego(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void aceptar(PowerUpVisitor visitador) {
		visitador.visitar(this);
	}
}
