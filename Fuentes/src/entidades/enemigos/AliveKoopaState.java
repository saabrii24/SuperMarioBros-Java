package entidades.enemigos;

import entidades.enemigos.KoopaTroopa.KoopaState;
import logica.Juego;

public class AliveKoopaState implements KoopaState{
	protected KoopaTroopa koopa;
	
	public AliveKoopaState (KoopaTroopa koopa) {
		this.koopa = koopa;	
	}
	
	@Override
	public void cambiar_estado() {
		koopa.set_estado(new DeadKoopaState(koopa));
	}

	public void mover() {
	        switch (koopa.get_direccion()) {
	            case 1 -> koopa.mover_derecha();
	            case -1 -> koopa.mover_izquierda();
	            default -> koopa.detener_movimiento();
	        }	
	}

	public void actualizar_sprite() {
        if (koopa.get_velocidad_en_x() != 0) {
        	koopa.cambiar_sprite((koopa.get_movimiento_derecha() ?
            	Juego.get_instancia().get_fabrica_sprites().get_koopa_movimiento_derecha():
            		Juego.get_instancia().get_fabrica_sprites().get_koopa_movimiento_izquierda()));
        }
		
	}

	@Override
	public boolean en_movimiento() {
		return true;
	}

	@Override
	public boolean mata_tocando() {
		return false;
	}


}
