package entidades.mario;

import entidades.enemigos.Enemigo;

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
    	if (mario.get_velocidad_x() < 0) {
        	mario.cambiar_sprite(mario.get_sprite_factory().get_mario_star_movimiento_izquierda());
        } else if (mario.get_velocidad_x() > 0) {
        	mario.cambiar_sprite(mario.get_sprite_factory().get_mario_star_movimiento_derecha());
        } else if(mario.get_velocidad_x() == 0){
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ? mario.get_sprite_factory().get_mario_star_ocioso_derecha() : mario.get_sprite_factory().get_mario_star_ocioso_izquierda());
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

