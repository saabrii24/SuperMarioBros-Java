package entidades.mario;

import entidades.BolaDeFuego;
import entidades.Entidad;
import entidades.enemigos.*;
import entidades.plataformas.*;
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
    	mario.cambiar_estado(new StarMarioState(mario,this));
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
	public void finalizar_invulnerabilidad() {}
	

	public int colision_con_enemigo(BuzzyBeetle buzzy){
		int valor=0;
		if(mario.get_limites_superiores().intersects(buzzy.get_limites_inferiores()))
			valor=1;
		else 
			valor=-1;
		return valor;
	}
	
	public int colision_con_enemigo(Goomba goomba) {
		int valor=0;
		if(mario.get_limites_superiores().intersects(goomba.get_limites_inferiores()))
			valor=1;
		else 
			valor=-1;
		return valor;
		
	}
	
	public int colision_con_enemigo(KoopaTroopa koopa) {
		int valor=0;
		if(mario.get_limites_superiores().intersects(koopa.get_limites_inferiores()))
			valor=koopa.cambiar_estado();
		else 
			valor=-1;
		return valor;
	}
	
	public int colision_con_enemigo(Lakitu lakitu) {
		int valor=0;
		if(mario.get_limites_superiores().intersects(lakitu.get_limites_inferiores()))
			valor=1;
		else 
			valor=-1;
		return valor;
	}
	
	public int colision_con_enemigo(PiranhaPlant piranha) {
		return -1;
	}
	
	public int colision_con_enemigo(Spiny spiny) {
		return -1;
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