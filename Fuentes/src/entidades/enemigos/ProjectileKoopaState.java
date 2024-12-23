package entidades.enemigos;

import entidades.mario.Mario;
import logica.Juego;

public class ProjectileKoopaState implements KoopaTroopa.KoopaState {
    private final KoopaTroopa koopa;

    public ProjectileKoopaState(KoopaTroopa koopa) { this.koopa = koopa; }

    public boolean cambiar_estado() {
    	return true;
    }

    public void actualizar_sprite() {
        koopa.cambiar_sprite(Juego.get_instancia().get_fabrica_sprites().get_koopa_proyectil());
    }

    public void mover() {
        if (koopa.get_direccion() == 1) mover_derecha();
        else if (koopa.get_direccion() == -1) mover_izquierda();
        else koopa.detener_movimiento();
    }

    private void mover_izquierda() { koopa.set_velocidad_en_x(-10); koopa.set_movimiento_derecha(false); }
    private void mover_derecha() { koopa.set_velocidad_en_x(10); koopa.set_movimiento_derecha(true); }
    public boolean en_movimiento() { return true; }
    public boolean mata_tocando() { return true; }

	public void visitar_enemigo(Enemigo enemigo) {
		Mario.get_instancia().get_sistema_puntuacion().sumar_puntos(enemigo.calcular_puntaje());
		enemigo.destruir(Juego.get_instancia().get_mapa_nivel_actual());
	}
}