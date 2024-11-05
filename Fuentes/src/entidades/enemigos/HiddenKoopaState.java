package entidades.enemigos;

import entidades.mario.Mario;
import logica.Juego;

public class HiddenKoopaState implements KoopaTroopa.KoopaState {
    private final KoopaTroopa koopa;

    public HiddenKoopaState(KoopaTroopa koopa) { this.koopa = koopa; }

    public boolean cambiar_estado() {
        koopa.set_estado(new ProjectileKoopaState(koopa));
        Mario mario = Mario.get_instancia();
        int ajuste_posicion = (koopa.get_direccion() == 1) ? -48 : 48;
        mario.set_posicion_en_x(mario.get_posicion_en_x() + ajuste_posicion);
        return false;
    }

    public void mover() { koopa.detener_movimiento(); }

    public void actualizar_sprite() {
        koopa.cambiar_sprite(Juego.get_instancia().get_fabrica_sprites().get_koopa_escondido());
    }

    public boolean en_movimiento() { return false; }

    public boolean mata_tocando() { return false; }
    
	public void visitar_enemigo(Enemigo enemigo) {}
}