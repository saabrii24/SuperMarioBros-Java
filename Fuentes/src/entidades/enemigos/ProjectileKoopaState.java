package entidades.enemigos;

import entidades.enemigos.KoopaTroopa.KoopaState;

public class ProjectileKoopaState implements KoopaState {
	protected KoopaTroopa koopa;
	
	public ProjectileKoopaState(KoopaTroopa koopa) {
		this.koopa = koopa;	
	}

	@Override
	public void cambiar_estado() {	
	}
	@Override
	public void actualizar_sprite() {
		
	}

	@Override
	public void mover() {
		 switch (koopa.get_direccion()) {
         case 1 -> koopa.mover_derecha();
         case -1 -> koopa.mover_izquierda();
         default -> koopa.detener_movimiento();
     }	
	}

	@Override
	public boolean en_movimiento() {
		return true;
	}

	@Override
	public boolean mata_tocando() {
		return true;
	}
}
