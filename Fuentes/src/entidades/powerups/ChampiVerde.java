package entidades.powerups;

import fabricas.Sprite;

public class ChampiVerde extends PowerUp{

	public ChampiVerde(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void aceptar(PowerUpVisitor visitante) {
		visitante.visitar(this);
	}

}
