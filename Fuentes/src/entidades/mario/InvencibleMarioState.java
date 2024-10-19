package entidades.mario;

import entidades.enemigos.Enemigo;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.SuperChampi;

public class InvencibleMarioState implements Mario.MarioState {
    private Mario mario;
    private Mario.MarioState estado_anterior;
    private long tiempo_inicio;
    private static final long DURACION_ESTRELLA = 10000;

    public InvencibleMarioState(Mario mario) {
        this.mario = mario;
        this.estado_anterior = mario.get_estado_anterior();
        this.tiempo_inicio = System.currentTimeMillis();
    }
    
    public void actualizar_sprite() {

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
        if (System.currentTimeMillis() - tiempo_inicio > DURACION_ESTRELLA) {
            mario.cambiar_estado(estado_anterior);
            return false;
        }
        return true; // Star Mario mata enemigos al tocarlos
    }

    public void finalizar_invulnerabilidad() {
        mario.cambiar_estado(estado_anterior); // Vuelve al estado previo al efecto de la Estrella
    }

}

