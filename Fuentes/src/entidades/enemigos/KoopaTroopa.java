package entidades.enemigos;

import entidades.mario.Mario;
import fabricas.Sprite;
import fabricas.SpritesFactory;

public class KoopaTroopa extends Enemigo {

    private SpritesFactory sprites_factory;

    public KoopaTroopa(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        this.sprites_factory = null;
        set_velocidad_en_x(3);
    }

    public interface KoopaState {
        // Define el estado de Koopa aquí si es necesario
    }

    protected void actualizar_posicion() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            set_posicion_en_y((get_posicion_en_y() - velocidad_en_y));
        }

        set_posicion_en_x(get_posicion_en_x() + velocidad_en_x);

        if (Mario.get_instancia() != null && Mario.get_instancia().get_sprite_factory() != null) {
            sprites_factory = Mario.get_instancia().get_sprite_factory();
            actualizar_sprite();
        }
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
                sprites_factory.get_koopa_movimiento_derecha() :
                sprites_factory.get_koopa_movimiento_izquierda());
        }
    }
}
