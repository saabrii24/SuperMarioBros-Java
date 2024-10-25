package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;

public class FireMarioState implements Mario.MarioState {
    private Mario mario;

    public FireMarioState(Mario mario) {
        this.mario = mario;
    }
	
	public void consumir_estrella() {
		mario.cambiar_estado(new InvencibleMarioState(mario));
		mario.get_sistema_puntuacion().sumar_puntos(30);
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

    public void disparar_bola_de_fuego() {
        mario.disparar(); // Mario lanza una bola de fuego
    }

	@Override
	public void actualizar_sprite() {
    	if(mario.esta_saltando() || mario.get_velocidad_en_y() < 0) { // Saltando o cayendo (velocidad negativa)
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ?
        			mario.get_sprite_factory().get_supermario_star_saltando_derecha() : 
        			mario.get_sprite_factory().get_supermario_star_saltando_izquierda());
        } else if (mario.get_velocidad_en_x() != 0 && !mario.esta_saltando()) {
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                    mario.get_sprite_factory().get_supermario_star_movimiento_derecha() : 
                    mario.get_sprite_factory().get_supermario_star_movimiento_izquierda());
        } else {
        	 mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                     mario.get_sprite_factory().get_supermario_star_ocioso_derecha() : 
                     mario.get_sprite_factory().get_supermario_star_ocioso_izquierda());
        }
    }

	@Override
	public void finalizar_invulnerabilidad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean mata_tocando() {
		return false;
	}

	@Override
	public boolean rompe_bloque() {
		return true;
	}

	@Override
	public boolean colision_con_enemigo(Enemigo enemigo) {
		mario.get_sistema_puntuacion().restar_puntos(enemigo.calcular_penalizacion());
    	mario.cambiar_estado(new NormalMarioState(mario));
    	return false;
	}

	public BolaDeFuego disparar() {
		return new BolaDeFuego(mario.get_posicion_en_x(), mario.get_posicion_en_y()+26,
				mario.get_sprite_factory().get_bola_de_fuego());
	}
	
}
