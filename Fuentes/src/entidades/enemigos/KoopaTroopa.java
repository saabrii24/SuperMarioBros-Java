package entidades.enemigos;

import fabricas.Sprite;

public class KoopaTroopa extends Enemigo{

	public KoopaTroopa(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public interface KoopaState {
		
	}
}
