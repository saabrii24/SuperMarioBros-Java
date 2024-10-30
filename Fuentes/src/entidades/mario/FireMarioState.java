package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.BuzzyBeetle;
import entidades.enemigos.Enemigo;
import entidades.enemigos.Goomba;
import entidades.enemigos.KoopaTroopa;
import entidades.enemigos.Lakitu;
import entidades.enemigos.PiranhaPlant;
import entidades.enemigos.Spiny;
import logica.Juego;

public class FireMarioState implements Mario.MarioState {
    private Mario mario;
    private static final long PROYECTIL_COOLDOWN = 1000;
    private long tiempo_ultimo_proyectil = 0;

    public FireMarioState(Mario mario) {
        this.mario = mario;
    }

    public void actualizar_sprite() {
        if (mario.esta_saltando()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_star_saltando_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_star_saltando_izquierda());
        } else if (mario.esta_en_movimiento()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_star_movimiento_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_star_movimiento_izquierda());
        } else {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_star_ocioso_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_star_ocioso_izquierda());
        }
    }

    public BolaDeFuego disparar() {
        if (System.currentTimeMillis() - tiempo_ultimo_proyectil >= PROYECTIL_COOLDOWN) {
            tiempo_ultimo_proyectil = System.currentTimeMillis();
            return new BolaDeFuego(mario.get_posicion_en_x(), mario.get_posicion_en_y() + 26, Juego.get_instancia().get_fabrica_sprites().get_bola_de_fuego());
        }
        return null;
    }

    public boolean rompe_bloque() { return true; }
    public boolean mata_tocando() { return false; }
	public boolean colision_con_enemigo(Enemigo enemigo) {
	    mario.cambiar_estado(new InvulnerableMarioState(mario));
	    return false;
	}

	public void consumir_estrella() {
		mario.cambiar_estado(new StarMarioState(mario,this));
		mario.get_sistema_puntuacion().sumar_puntos(30);
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	@Override
	public void finalizar_invulnerabilidad() {
	}
	
	public int colision_con_enemigo(BuzzyBeetle buzzy){
		return 0;
		
	}
	
	public int colision_con_enemigo(Goomba goomba) {
		return 0;
		
	}
	
	public int colision_con_enemigo(KoopaTroopa koopa) {
		return 0;
	}
	
	public int colision_con_enemigo(Lakitu lakitu) {
		return 0;
	}
	
	public int colision_con_enemigo(PiranhaPlant piranha) {
		return 0;
	}
	
	public int colision_con_enemigo(Spiny spiny) {
		return 0;
	}
}