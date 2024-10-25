package entidades.enemigos;

import entidades.enemigos.KoopaTroopa.KoopaState;
import entidades.mario.Mario;
import logica.Juego;

public class HiddenKoopaState implements KoopaState {
	protected KoopaTroopa koopa;
	
	public HiddenKoopaState(KoopaTroopa koopa) {
		this.koopa = koopa;	
	}

	@Override
	public void cambiar_estado() {
		koopa.set_estado(new ProjectileKoopaState(koopa));
		if(koopa.get_direccion() == 1)
			Mario.get_instancia().set_posicion_en_x(Mario.get_instancia().get_posicion_en_x() - 48);
		else 
			Mario.get_instancia().set_posicion_en_x(Mario.get_instancia().get_posicion_en_x() + 48);
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
