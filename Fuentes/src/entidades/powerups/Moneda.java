package entidades.powerups;

import fabricas.Sprite;

public class Moneda extends PowerUp{

	private static final long serialVersionUID = 1L;

	public Moneda(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void aceptar(PowerUpVisitor visitador) {
		visitador.visitar(this);
	}

}
