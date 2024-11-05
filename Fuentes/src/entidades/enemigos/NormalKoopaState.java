package entidades.enemigos;

import entidades.mario.Mario;
import fabricas.Sprite;
import logica.Juego;

public class NormalKoopaState implements KoopaTroopa.KoopaState {
    private final KoopaTroopa koopa;

    public NormalKoopaState(KoopaTroopa koopa) { this.koopa = koopa; }

    public boolean cambiar_estado() {
        koopa.set_estado(new HiddenKoopaState(koopa));
        Mario mario = Mario.get_instancia();
        int ajuste_posicion = (koopa.get_direccion() == 1) ? -48 : 48;
        mario.set_posicion_en_x(mario.get_posicion_en_x() + ajuste_posicion);
        return false;
    }

    public void mover() {
        if (koopa.get_direccion() == 1) koopa.mover_derecha();
        else if (koopa.get_direccion() == -1) koopa.mover_izquierda();
        else koopa.detener_movimiento();
    }
    
    public void actualizar_sprite() {
        if (koopa.get_velocidad_en_x() != 0) {
            Sprite sprite = koopa.get_movimiento_derecha()
                ? Juego.get_instancia().get_fabrica_sprites().get_koopa_movimiento_derecha()
                : Juego.get_instancia().get_fabrica_sprites().get_koopa_movimiento_izquierda();
            koopa.cambiar_sprite(sprite);
        }
    }

    public boolean en_movimiento() { return true; }

    public boolean mata_tocando() { return false; }

	public void visitar_enemigo(Enemigo enemigo) {}
}