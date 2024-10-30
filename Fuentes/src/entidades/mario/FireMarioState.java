package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;

public class FireMarioState implements Mario.MarioState {
    private Mario mario;
    private static final long PROYECTIL_COOLDOWN = 1000;
    private long tiempo_ultimo_proyectil = 0;

    public FireMarioState(Mario mario) {
        this.mario = mario;
    }
	
	public void consumir_estrella() {
		mario.cambiar_estado(new InvencibleMarioState(mario,this));
		mario.get_sistema_puntuacion().sumar_puntos(30);
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

    public void disparar_bola_de_fuego() {
        mario.disparar();
    }

	@Override
	public void actualizar_sprite() {
    	if(mario.get_contador_saltos() == 1 ||  mario.get_velocidad_en_y() < 0 || mario.get_velocidad_en_y() > 0.4) { // Saltando o cayendo (velocidad negativa)
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ?
        			mario.get_fabrica_sprites().get_supermario_star_saltando_derecha() : 
        			mario.get_fabrica_sprites().get_supermario_star_saltando_izquierda());
        } else if (mario.get_velocidad_en_x() != 0 && !mario.esta_saltando()) {
        	mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                    mario.get_fabrica_sprites().get_supermario_star_movimiento_derecha() : 
                    mario.get_fabrica_sprites().get_supermario_star_movimiento_izquierda());
        } else {
        	 mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                     mario.get_fabrica_sprites().get_supermario_star_ocioso_derecha() : 
                     mario.get_fabrica_sprites().get_supermario_star_ocioso_izquierda());
        }
    }

	@Override
	public void finalizar_invulnerabilidad() {}

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

	@Override
    public BolaDeFuego disparar() {
        long tiempo_actual = System.currentTimeMillis();
        if (tiempo_actual - tiempo_ultimo_proyectil >= PROYECTIL_COOLDOWN) {
            tiempo_ultimo_proyectil = tiempo_actual;
            return new BolaDeFuego(mario.get_posicion_en_x(), 
                mario.get_posicion_en_y()+26,
                mario.get_fabrica_sprites().get_bola_de_fuego()
            );
        }
        return null;
    }
	
}
