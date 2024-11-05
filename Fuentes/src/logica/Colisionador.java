package logica;

import entidades.mario.Mario;
import entidades.plataformas.*;
import entidades.powerups.PowerUp;
import entidades.*;
import entidades.enemigos.*;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class Colisionador {
    private Mapa mapa;
    private boolean murio_mario;

    public Colisionador(Mapa mapa) {
        this.mapa = mapa;
        this.murio_mario = false;
    }
  
    public void verificar_colisiones(Juego juego) {
        Mario mario = Mario.get_instancia();
        verificar_enemigo_cayo_al_vacio();
        verificar_colisiones_con_plataformas(mario);
        verificar_colisiones_con_enemigos(mario);
        verificar_colisiones_con_powerups(mario);
        verificar_colisiones_enemigos_con_enemigos();
        verificar_colisiones_proyectiles();
    }

	private void verificar_enemigo_cayo_al_vacio() {
        List<Enemigo> enemigos = new ArrayList<>(mapa.get_entidades_enemigos());
        for (Enemigo enemigo : enemigos) {
            if (enemigo.get_posicion_en_y() <= 0) {
                enemigo.eliminar_del_mapa();
            }
        }
	}

	private void verificar_colisiones_con_plataformas(Mario mario) {
        verificar_colisiones_con_plataformas_mario(mario);
        verificar_colisiones_plataformas_enemigos();
    }
	
    private void verificar_colisiones_con_plataformas_mario(Mario mario) {
    	for(Plataforma plataforma: mapa.get_entidades_plataformas()) {
    		if(mario.get_limites_superiores().intersects(plataforma.get_limites_inferiores())) {
    			if(plataforma.aceptar(mario))
    				ajustar_posicion_mario_sobre_plataforma(mario,plataforma);
    		}
    		else {
    			if(mario.get_limites_inferiores().intersects(plataforma.get_limites_superiores())|| mario.get_limites_derecha().intersects(plataforma.get_limites_izquierda()) || mario.get_limites_izquierda().intersects(plataforma.get_limites_derecha()))
    				plataforma.aceptar(mario);
    		}
    	}
	}   
    
    private void ajustar_posicion_mario_sobre_plataforma(Mario mario, Entidad plataforma) {
        mario.set_contador_saltos(0);
        mario.set_velocidad_en_y(0);
        mario.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
    }    
    
    private void verificar_colisiones_plataformas_enemigos() {
        List<Enemigo> enemigos = mapa.get_entidades_enemigos();
        for (Enemigo enemigo : enemigos) {
            manejar_colision_vertical(enemigo);
            manejar_colision_horizontal(enemigo);
        }
    }
    
    private void manejar_colision_vertical(Enemigo enemigo) {
        for (Plataforma plataforma : mapa.get_entidades_plataformas()) {
        	if (enemigo.get_limites_superiores().intersects(plataforma.get_limites_inferiores()))
        			plataforma.aceptar(enemigo);
	     }
    }   
    
    private void manejar_colision_horizontal(Enemigo enemigo) {
        if (colisiona_con_plataforma(enemigo.get_limites_derecha())) {
        	enemigo.set_direccion(-1);
        } else if (colisiona_con_plataforma(enemigo.get_limites_izquierda())) {
        	enemigo.set_direccion(1);
        }
    }    
    
    private boolean colisiona_con_plataforma(Rectangle limites) {
        return mapa.get_entidades_plataformas().stream()
            .anyMatch(plataforma -> plataforma.get_limites().intersects(limites));
    }
 
    private void verificar_colisiones_con_enemigos(Mario mario) {
        for (Enemigo enemigo : mapa.get_entidades_enemigos()) {
            if (mario.get_limites().intersects(enemigo.get_limites())) {
            	murio_mario=enemigo.aceptar(mario);
            }
        }
    }
    
    private void verificar_colisiones_enemigos_con_enemigos() {
    	for(Enemigo enemigo1: mapa.get_entidades_enemigos())
    		for(Enemigo enemigo2: mapa.get_entidades_enemigos())
    			if(enemigo1!=enemigo2 && enemigo1.get_limites().intersects(enemigo2.get_limites())) 
    				enemigo1.aceptar(enemigo2);
   	}
   
    private void verificar_colisiones_con_powerups(Mario mario) {
        for (PowerUp power_up : new ArrayList<>(mapa.get_entidades_powerup())) {
            if (mario.get_limites().intersects(power_up.get_limites())) {
                power_up.aceptar(mario);
                power_up.destruir(mapa);
            }
        }
    }
    
    private void verificar_colisiones_proyectiles() {
    	for (BolaDeFuego proyectil : new ArrayList<>(mapa.get_entidades_proyectiles())) {
            if (colisiona_con_plataforma(proyectil.get_limites())) {
                proyectil.destruir(mapa);
                continue;
            }   
            verificar_colision_proyectil_enemigos(proyectil);
        }
    }   
    
    private void verificar_colision_proyectil_enemigos(BolaDeFuego proyectil) {
        for (Enemigo enemigo : mapa.get_entidades_enemigos()) {
            if (proyectil.get_limites().intersects(enemigo.get_limites())) {
                ((Enemigo) enemigo).destruir(mapa);
                Mario.get_instancia().get_sistema_puntuacion().sumar_puntos(((Enemigo) enemigo).calcular_puntaje());
                proyectil.destruir(mapa);
                break;
            }
        }
    }
 
    public boolean get_murio_mario() { return murio_mario; }    
    public void set_murio_mario(boolean murio) { murio_mario = murio; }
}