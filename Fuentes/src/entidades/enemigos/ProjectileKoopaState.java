package entidades.enemigos;

import entidades.enemigos.KoopaTroopa.KoopaState;
import logica.Juego;

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
		koopa.cambiar_sprite(Juego.get_instancia().get_fabrica_sprites().get_koopa_proyectil());
	}

	@Override
	public void mover() {
		 switch (koopa.get_direccion()) {
         case 1 -> this.mover_derecha();
         case -1 -> this.mover_izquierda();
         default -> koopa.detener_movimiento();
     }	
	}
	
    protected void mover_izquierda() {
        koopa.set_velocidad_en_x(-10);
        koopa.set_movimiento_derecha(false);
    }

    protected void mover_derecha() {
        koopa.set_velocidad_en_x(10);
        koopa.set_movimiento_derecha(true);
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
