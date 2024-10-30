package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import logica.Juego;

public class InvencibleMarioState implements Mario.MarioState {
    private Mario mario;
    private Mario.MarioState estado_anterior;
    private long tiempo_inicio;
    private static final long DURACION_ESTRELLA = 10000;

    public InvencibleMarioState(Mario mario, Mario.MarioState estado_anterior) {
        this.mario = mario;
        this.estado_anterior = estado_anterior;
        this.tiempo_inicio = System.currentTimeMillis();
    }
    
    public void actualizar_sprite() {
        if (mario.esta_saltando()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_saltando_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_saltando_izquierda());
        } else if (mario.esta_en_movimiento()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_movimiento_derecha() : 
                Juego.get_instancia().get_fabrica_sprites().get_mario_star_movimiento_izquierda());
        } else {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_ocioso_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_mario_star_ocioso_izquierda());
        }
    }

    public void finalizar_invulnerabilidad() {
        if (System.currentTimeMillis() - tiempo_inicio > DURACION_ESTRELLA) {
            mario.cambiar_estado(estado_anterior != null ? estado_anterior : new NormalMarioState(mario));
        }
    }

    public boolean rompe_bloque() { return false; }
    public boolean mata_tocando() { return true; }
    public boolean colision_con_enemigo(Enemigo enemigo) { return false; }
    public BolaDeFuego disparar() { return null; }

    public void consumir_estrella() {
    	mario.get_sistema_puntuacion().sumar_puntos(35);
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}
}