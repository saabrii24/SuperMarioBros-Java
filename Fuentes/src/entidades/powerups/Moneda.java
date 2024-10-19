package entidades.powerups;

import entidades.mario.Mario;
import fabricas.Sprite;

public class Moneda extends PowerUp{

	private static final long serialVersionUID = 1L;

	public Moneda(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public ListaPowerUps get_tipo() {
		return ListaPowerUps.MONEDA;
	}

	@Override
	public void aplicar_efecto(Mario mario) {
		// TODO Auto-generated method stub
		
	}

}
