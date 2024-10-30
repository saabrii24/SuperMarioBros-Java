package entidades.mario;

import entidades.BolaDeFuego;
import entidades.enemigos.*;
import logica.Juego;

public class SuperMarioState implements Mario.MarioState {
    private Mario mario;

    public SuperMarioState(Mario mario) {
        this.mario = mario;
        mario.set_estado(this);
    }
    
    public void actualizar_sprite() {
        if (mario.esta_saltando()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
                Juego.get_instancia().get_fabrica_sprites().get_supermario_saltando_derecha() : 
                Juego.get_instancia().get_fabrica_sprites().get_supermario_saltando_izquierda());
        } else if (mario.esta_en_movimiento()) {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_movimiento_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_movimiento_izquierda());
        } else {
            mario.cambiar_sprite(mario.get_movimiento_derecha() ? 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_ocioso_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_supermario_ocioso_izquierda());
        }
    }
    
	public void consumir_estrella() {
		mario.get_sistema_puntuacion().sumar_puntos(30);
		mario.cambiar_estado(new StarMarioState(mario,this));
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(30);
		mario.cambiar_estado(new FireMarioState(mario));
	}

	public boolean colision_con_enemigo(Enemigo enemigo) {
	    mario.cambiar_estado(new InvulnerableMarioState(mario));
	    return false;
	}

    public boolean rompe_bloque() { return true; }
    public boolean mata_tocando() { return false; }
    public BolaDeFuego disparar() { return null; }

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
}

