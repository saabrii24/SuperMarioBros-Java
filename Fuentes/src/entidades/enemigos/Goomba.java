package entidades.enemigos;

import fabricas.Sprite;

public class Goomba extends Enemigo {
    public Goomba(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        set_velocidad_en_x(3);
    }

    public void actualizar() {
        if (esta_cayendo()) {
            set_velocidad_en_y(get_velocidad_y() + GRAVEDAD);
            set_posicion_en_y(get_posicion_en_y() - get_velocidad_y());
        }
        set_posicion_en_x(get_posicion_en_x() + get_velocidad_x());
    }

    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    protected void mover_izquierda() {
        set_velocidad_en_x(-2);
        movimiento_derecha = false;
    }

    protected void mover_derecha() {
        set_velocidad_en_x(2);
        set_movimiento_derecha(true);
        movimiento_derecha = true;
    }

    protected void detener_movimiento() {
        set_velocidad_en_x(0);
    }
}

