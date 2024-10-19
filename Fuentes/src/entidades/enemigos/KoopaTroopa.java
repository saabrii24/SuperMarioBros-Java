package entidades.enemigos;

import fabricas.Sprite;
import fabricas.SpritesFactory;

public class KoopaTroopa extends Enemigo{

    private SpritesFactory sprites_factory;
    
	public KoopaTroopa(int x, int y, Sprite sprite) {
		super(x, y, sprite);
        this.sprites_factory = null;
		set_velocidad_en_x(3);
	}
	
	public interface KoopaState {
		
	}
	
	/*
	public void actualizar_posicion() {
        super.actualizar_posicion();
		actualizar_sprite();
	}
	*/

	private void actualizar_sprite() {
		if (velocidad_en_x != 0) {
            set_sprite(movimiento_derecha ? 
                sprites_factory.get_koopa_movimiento_derecha() : 
                sprites_factory.get_mario_movimiento_izquierda());
		}
	}
}
