package entidades.powerups;

import fabricas.Sprite;

public class SuperChampi extends PowerUp{

	public SuperChampi(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public void aceptar(PowerUpVisitor visitador) {
		visitador.visitar(this);
	}
	
}
