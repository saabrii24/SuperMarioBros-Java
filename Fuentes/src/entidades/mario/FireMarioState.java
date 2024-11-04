package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.BuzzyBeetle;
import entidades.enemigos.Enemigo;
import entidades.enemigos.Goomba;
import entidades.enemigos.KoopaTroopa;
import entidades.enemigos.Lakitu;
import entidades.enemigos.PiranhaPlant;
import entidades.enemigos.Spiny;
import entidades.plataformas.*;
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
	
	public boolean colision_con_enemigo(BuzzyBeetle buzzy){
		boolean murio_mario=false;
		if(mario.get_limites_superiores().intersects(buzzy.get_limites_inferiores())) {
			buzzy.destruir(Juego.get_instancia().get_mapa_nivel_actual());
			mario.get_sistema_puntuacion().sumar_puntos(buzzy.calcular_puntaje());
		}
		else 
			murio_mario=mario.colision_con_enemigo(buzzy);
		return murio_mario;
	}
	
	public boolean colision_con_enemigo(Goomba goomba) {
		boolean murio_mario=false;
		if(mario.get_limites_superiores().intersects(goomba.get_limites_inferiores())) {
			goomba.destruir(Juego.get_instancia().get_mapa_nivel_actual());
			mario.get_sistema_puntuacion().sumar_puntos(goomba.calcular_puntaje());
		}
		else 
			murio_mario=mario.colision_con_enemigo(goomba);
		return murio_mario;
	}
	
	public boolean colision_con_enemigo(KoopaTroopa koopa) {
		boolean murio_mario=false;
		if(mario.get_limites_superiores().intersects(koopa.get_limites_inferiores()))
			murio_mario=koopa.cambiar_estado();
		else 
			murio_mario=mario.colision_con_enemigo(koopa);
		return murio_mario;
	}
	
	public boolean colision_con_enemigo(Lakitu lakitu) {
		boolean murio_mario=false;
		if(mario.get_limites_superiores().intersects(lakitu.get_limites_inferiores())) {
			lakitu.destruir(Juego.get_instancia().get_mapa_nivel_actual());
			mario.get_sistema_puntuacion().sumar_puntos(lakitu.calcular_puntaje());
		}
		else 
			murio_mario=mario.colision_con_enemigo(lakitu);
		return murio_mario;
	}
	
	public boolean colision_con_enemigo(PiranhaPlant piranha) {
		return mario.colision_con_enemigo(piranha);
	}
	
	public boolean colision_con_enemigo(Spiny spiny) {
		return mario.colision_con_enemigo(spiny);
	}

	@Override
	public void colision_con_plataformas(BloqueDePregunta bloque_de_pregunta) {
		if(mario.get_limites_inferiores().intersects(bloque_de_pregunta.get_limites_superiores())) {
			ajustar_posicion_mario_bajo_plataforma(mario,bloque_de_pregunta);
			mario.set_contador_saltos(1);
			bloque_de_pregunta.destruir(Juego.get_instancia().get_mapa_nivel_actual());
		}
		else {
			if(mario.get_limites_derecha().intersects(bloque_de_pregunta.get_limites_izquierda()))
				mario.set_posicion_en_x(bloque_de_pregunta.get_posicion_en_x() - mario.get_dimension().width);
			else 

				mario.set_posicion_en_x(bloque_de_pregunta.get_posicion_en_x() + bloque_de_pregunta.get_dimension().width);
		}
	}

	@Override
	public void colision_con_plataformas(BloqueSolido bloque_solido) {
		if(mario.get_limites_inferiores().intersects(bloque_solido.get_limites_superiores())) {
			ajustar_posicion_mario_bajo_plataforma(mario,bloque_solido);
			mario.set_contador_saltos(1);
		}
		else {
			if(mario.get_limites_derecha().intersects(bloque_solido.get_limites_izquierda()))
				mario.set_posicion_en_x(bloque_solido.get_posicion_en_x() - mario.get_dimension().width);
			else 
				mario.set_posicion_en_x(bloque_solido.get_posicion_en_x() + bloque_solido.get_dimension().width);
				
		}
	}

	@Override
	public void colision_con_plataformas(LadrilloSolido ladrillo_solido) {
		if(mario.get_limites_inferiores().intersects(ladrillo_solido.get_limites_superiores())) {
			ajustar_posicion_mario_bajo_plataforma(mario,ladrillo_solido);
			mario.set_contador_saltos(1);
			ladrillo_solido.destruir(Juego.get_instancia().get_mapa_nivel_actual());
		}
		else {
			if(mario.get_limites_derecha().intersects(ladrillo_solido.get_limites_izquierda()))
				mario.set_posicion_en_x(ladrillo_solido.get_posicion_en_x() - mario.get_dimension().width);
			else
				mario.set_posicion_en_x(ladrillo_solido.get_posicion_en_x() + ladrillo_solido.get_dimension().width);
		}
	}

	@Override
	public void colision_con_plataformas(Tuberias tuberia) {
		if(mario.get_limites_derecha().intersects(tuberia.get_limites_izquierda()))
			mario.set_posicion_en_x(tuberia.get_posicion_en_x() - mario.get_dimension().width);
		else
			mario.set_posicion_en_x(tuberia.get_posicion_en_x() + tuberia.get_dimension().width);
	}
	
	 
	 private void ajustar_posicion_mario_bajo_plataforma(Mario mario, Plataforma plataforma) {
	        mario.set_posicion_en_y(plataforma.get_posicion_en_y() - mario.get_dimension().height + 1);
	        mario.set_cayendo(true);
	        mario.set_velocidad_en_y(0);
		}
}