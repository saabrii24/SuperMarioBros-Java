package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.*;
import entidades.plataformas.*;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.SuperChampi;
import logica.Juego;

public class InvulnerableMarioState implements Mario.MarioState {
    private Mario mario;
    private Mario.MarioState estado_anterior;
    private long tiempo_inicio;
    private static final long DURACION_INVULNERABLE = 1000; // 1 segundo

    public InvulnerableMarioState(Mario mario) {
        this.mario = mario;
        this.estado_anterior = new NormalMarioState(mario);
        this.tiempo_inicio = System.currentTimeMillis();
        mario.set_estado(this);
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
        if (System.currentTimeMillis() - tiempo_inicio >= DURACION_INVULNERABLE) {
            mario.cambiar_estado(estado_anterior);
        }
    }

    public boolean rompe_bloque() { return estado_anterior.rompe_bloque(); }
    public boolean mata_tocando() { return false; }
    public BolaDeFuego disparar() { return estado_anterior.disparar(); }

    public void consumir(Estrella estrella) { estado_anterior.consumir(estrella); }
    public void consumir(SuperChampi super_champi) { estado_anterior.consumir(super_champi); }
    public void consumir(FlorDeFuego flor_de_fuego) { estado_anterior.consumir(flor_de_fuego); }

    // Métodos de colisión con enemigos
    public boolean colision_con_enemigo(Enemigo enemigo) { return false; }
    public boolean colision_con_enemigo(BuzzyBeetle buzzy) { return false; }
    public boolean colision_con_enemigo(Goomba goomba) { return false; }
    public boolean colision_con_enemigo(KoopaTroopa koopa) { return false; }
    public boolean colision_con_enemigo(Lakitu lakitu) { return false; }
    public boolean colision_con_enemigo(PiranhaPlant piranha) { return false; }
    public boolean colision_con_enemigo(Spiny spiny) { return false; }

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

	public void colision_con_plataformas(Tuberia tuberia) {
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
