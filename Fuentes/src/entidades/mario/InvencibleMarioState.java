package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;

public class InvencibleMarioState implements Mario.MarioState {
    private Mario mario;
    private Mario.MarioState estado_anterior;
    private long tiempo_inicio;
    private static final long DURACION_ESTRELLA = 5000;

    public InvencibleMarioState(Mario mario) {
        this.mario = mario;
        this.estado_anterior = mario.get_estado_anterior();
        this.tiempo_inicio = System.currentTimeMillis();
    }
    
    public void actualizar_sprite() {
    	if(mario.esta_saltando() || mario.get_velocidad_y() < 0) { // Saltando o cayendo (velocidad negativa)
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ?
        			mario.get_sprite_factory().get_mario_star_saltando_derecha() : 
        			mario.get_sprite_factory().get_mario_star_saltando_izquierda());
        } else if (mario.get_velocidad_x() != 0 && !mario.esta_saltando()) {
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                    mario.get_sprite_factory().get_mario_star_movimiento_derecha() : 
                    mario.get_sprite_factory().get_mario_star_movimiento_izquierda());
        } else {
        	 mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                     mario.get_sprite_factory().get_mario_star_ocioso_derecha() : 
                     mario.get_sprite_factory().get_mario_star_ocioso_izquierda());
        }
    }


    public void consumir_estrella() {
    	mario.get_sistema_puntuacion().sumar_puntos(35);
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

    public boolean matar_si_hay_colision(Enemigo enemigo) {
		return true;
    }
    
    public boolean mata_tocando() {
    	return true;
    }

    public void finalizar_invulnerabilidad() {
    	System.out.println("entre aca");
        if (System.currentTimeMillis() - tiempo_inicio > DURACION_ESTRELLA) {
        	if (estado_anterior != null) {
        		mario.cambiar_estado(estado_anterior);
        	}
        	else mario.cambiar_estado(new NormalMarioState(mario));
        }
    }

	@Override
	public boolean rompe_bloque() {
		return false;
	}
	@Override
	public boolean colision_con_enemigo(Enemigo enemigo) {
		return false;
	}

	@Override
	public BolaDeFuego disparar() {
		// TODO Auto-generated method stub
		return null;
	}

}

