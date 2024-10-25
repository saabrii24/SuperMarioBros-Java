package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
public class SuperMarioState implements Mario.MarioState {
    private Mario mario;

    public SuperMarioState(Mario mario) {
        this.mario = mario;
        mario.set_estado(this);
    }
    
    public void actualizar_sprite() {
    	if(mario.esta_saltando() || mario.get_velocidad_en_y() < 0) { // Saltando o cayendo (velocidad negativa)
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ?
        			mario.get_sprite_factory().get_supermario_saltando_derecha() : 
        			mario.get_sprite_factory().get_supermario_saltando_izquierda());
        } else if (mario.get_velocidad_en_x() != 0 && !mario.esta_saltando()) {
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                    mario.get_sprite_factory().get_supermario_movimiento_derecha() : 
                    mario.get_sprite_factory().get_supermario_movimiento_izquierda());
        } else {
        	 mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                     mario.get_sprite_factory().get_supermario_ocioso_derecha() : 
                     mario.get_sprite_factory().get_supermario_ocioso_izquierda());
        }
    }
	
	public void consumir_estrella() {
		mario.get_sistema_puntuacion().sumar_puntos(30);
		mario.cambiar_estado(new InvencibleMarioState(mario));
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(30);
		mario.cambiar_estado(new FireMarioState(mario));
	}

    public boolean matar_si_hay_colision() {
        // En estado Super Mario, si colisiona con un enemigo, vuelve al estado Normal
        mario.cambiar_estado(new NormalMarioState(mario));
        return false; // Mario no mata al enemigo, pero tampoco no pierde una vida
    }
    
    public boolean colision_con_enemigo(Enemigo enemigo) {
    	mario.get_sistema_puntuacion().restar_puntos(enemigo.calcular_penalizacion());
    	mario.cambiar_estado(new NormalMarioState(mario));
    	return false;
    }
    
	@Override
	public void finalizar_invulnerabilidad() {
	}
	
	public boolean rompe_bloque() {
		return true;
	}
	
	@Override
	public boolean mata_tocando() {
		return false;
	}

	@Override
	public BolaDeFuego disparar() {	
		return null;
	}
}

