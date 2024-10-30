package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import logica.Juego;

public class NormalMarioState implements Mario.MarioState {
    private Mario mario;

    public NormalMarioState(Mario mario) {
        this.mario = mario;
    }

    public void actualizar_sprite() {
        if (mario.esta_saltando()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_saltando_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_saltando_izquierda());
        } else if (mario.esta_en_movimiento()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_movimiento_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_movimiento_izquierda());
        } else {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_ocioso_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_ocioso_izquierda());
        }
    }

    public boolean colision_con_enemigo(Enemigo enemigo) {
        mario.get_sistema_puntuacion().restar_puntos(enemigo.calcular_penalizacion());
        mario.get_sistema_vidas().quitar_vida();
        return true;
    }

    public boolean rompe_bloque() { return false; }
    public boolean mata_tocando() { return false; }
    public BolaDeFuego disparar() { return null; }

    public void consumir_estrella() {
    	mario.get_sistema_puntuacion().sumar_puntos(20);
    	mario.cambiar_estado(new InvencibleMarioState(mario,this));
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(10);
    	mario.cambiar_estado(new SuperMarioState(mario));
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(5);
    	mario.cambiar_estado(new FireMarioState(mario));
	}

	@Override
	public void finalizar_invulnerabilidad() {
		// TODO Auto-generated method stub
		
	}
}