package entidades.powerups;

import entidades.mario.Mario;
import fabricas.Sprite;

public class Estrella extends PowerUp{

	private static final long serialVersionUID = 1L;

	public Estrella(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public ListaPowerUps get_tipo() {
		return ListaPowerUps.ESTRELLA;
	}

	@Override
	public void aplicar_efecto(Mario mario) {
		// TODO Auto-generated method stub
		
	}

}
