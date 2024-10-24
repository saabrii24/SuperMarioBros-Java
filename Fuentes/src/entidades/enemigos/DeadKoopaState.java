package entidades.enemigos;

import entidades.enemigos.KoopaTroopa.KoopaState;
import logica.Juego;

public class DeadKoopaState implements KoopaState {
	protected KoopaTroopa koopa;
	
	public DeadKoopaState(KoopaTroopa koopa) {
		this.koopa = koopa;	
	}

	@Override
	public void cambiar_estado() {
		koopa.set_estado(new ProjectileKoopaState(koopa));
		koopa.set_velocidad_en_x(10);
	}

	@Override
	public void mover() {
		koopa.set_velocidad_en_x(0);
	}
	public void actualizar_sprite() {
		koopa.cambiar_sprite(Juego.get_instancia().get_fabrica_sprites().get_koopa_escondido());
	}

	@Override
	public boolean en_movimiento() {
		return false;
	}

	@Override
	public boolean mata_tocando() {
		return false;
	}
}
