package entidades.mario;

import entidades.enemigos.Enemigo;

public class NormalMarioState implements Mario.MarioState {
    private Mario mario;

    public NormalMarioState(Mario mario) {
        this.mario = mario;
    }

    public void consumir_estrella() {
    	mario.get_sistema_puntuacion().sumar_puntos(20);
    	mario.cambiar_estado(new InvencibleMarioState(mario));
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(10);
    	mario.cambiar_estado(new SuperMarioState(mario));
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(5);
    	mario.cambiar_estado(new FireMarioState(mario));
	}

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        // En estado normal, Mario muere si colisiona con un enemigo
        mario.get_sistema_vidas().quitar_vida();
        mario.cambiar_estado(new NormalMarioState(mario));
        return false; // Mario no mata al enemigo
    }
    
    public void actualizar_sprite() {
    	System.out.println(mario.get_velocidad_x());
        if (mario.get_velocidad_x() < 0) {
        	mario.cambiar_sprite(mario.get_sprite_factory().get_mario_movimiento_izquierda());
        } else if (mario.get_velocidad_x() > 0) {
        	mario.cambiar_sprite(mario.get_sprite_factory().get_mario_movimiento_derecha());
        } else if(mario.get_velocidad_x() == 0){
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ? mario.get_sprite_factory().get_mario_ocioso_derecha() : mario.get_sprite_factory().get_mario_ocioso_izquierda());
        }
        //else if(mario.get_velocidad_en_y() > 0)
        	//mario.cambiar_sprite(mario.get_movimiento_derecha() == 1 ? mario.get_sprite_factory().get_mario_saltando_derecha() : mario.get_sprite_factory().get_mario_saltando_izquierda());
    }

}

