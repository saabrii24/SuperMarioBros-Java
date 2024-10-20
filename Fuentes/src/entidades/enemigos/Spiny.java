package entidades.enemigos;

import entidades.mario.Mario;
import fabricas.Sprite;
import fabricas.SpritesFactory;

public class Spiny extends Enemigo{
	
    private SpritesFactory sprites_factory;

	public Spiny(int x, int y, Sprite sprite) {
		super(x, y, sprite);
        this.sprites_factory = null;
		set_velocidad_en_x(3);
	}

    protected void actualizar_posicion() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }

        posicion_en_x += velocidad_en_x;
        
    }

    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    protected void mover_izquierda() {
        this.velocidad_en_x = -3;
        movimiento_derecha = false;
        actualizar_posicion();
    }

    protected void mover_derecha() {
        this.velocidad_en_x = 3;
        movimiento_derecha = true;
        actualizar_posicion();
    }

    protected void detener_movimiento() {
        this.velocidad_en_x = 0;
        actualizar_posicion();
    }

	private void actualizar_sprite() {
		if (velocidad_en_x != 0) {
            set_sprite(movimiento_derecha ? 
                sprites_factory.get_spiny_movimiento_derecha() : 
                sprites_factory.get_spiny_movimiento_izquierda());
		}
	}

}
