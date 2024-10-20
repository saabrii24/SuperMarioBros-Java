package entidades.enemigos;

import fabricas.Sprite;

public class Goomba extends Enemigo{
	public Goomba(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		set_velocidad_en_x(3);
	}

    protected void actualizar_posicion() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }

        if (saltando) {
            velocidad_en_y -= GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
            if (velocidad_en_y <= 0) {
                saltando = false;
                cayendo = true;
            }
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

}
