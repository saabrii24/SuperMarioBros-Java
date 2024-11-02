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
        verificar_colisiones_con_plataformas(mario);
        verificar_colisiones_con_enemigos(mario);
        verificar_colisiones_con_powerups(mario);
        verificar_colisiones_proyectiles();
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
    
	/*
     * colision enemigos plataforma ta
     * colision mario enemigos ta
     * colision mario pu ta
     * colision mario plataforma ta
     * colision bola de fuego ta
     * 
     */
    
    private void verificar_colisiones_plataformas_enemigos() {
        List<Enemigo> enemigos = mapa.get_entidades_enemigos();
        for (Enemigo enemigo : enemigos) {
            manejar_colision_vertical(enemigo);
            manejar_colision_horizontal(enemigo);
        }
    }
    
    private void verificar_colisiones_con_enemigos(Mario mario) {
        ResultadoColision resultado = ResultadoColision.NADIE_MUERE;
        for (Enemigo enemigo : mapa.get_entidades_enemigos()) {
            if (mario.get_limites().intersects(enemigo.get_limites())) {
                resultado=enemigo.aceptar(mario);
                if (resultado == ResultadoColision.ENEMIGO_MUERE) {
                    mario.get_sistema_puntuacion().sumar_puntos(enemigo.calcular_puntaje());
                    enemigo.destruir(mapa);
                } else if (resultado == ResultadoColision.MARIO_MUERE) {
                    murio_mario = mario.colision_con_enemigo(enemigo);
                }
            }
        }
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
    	verificar_colisiones_bolas_fuego();
    }
    
    private void verificar_colisiones_bolas_fuego() {
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
    
  	/*
    private void verificar_colisiones_koopa_proyectil() {
        Mario mario = Mario.get_instancia();
        for (KoopaTroopa koopa : new ArrayList<>(mapa.get_entidades_koopa_troopa())) {
            if (!koopa.mata_tocando()) {
                continue; // Si no está en modo proyectil, no hace nada
            }
            for (EntidadMovible enemigo : obtener_todos_enemigos()) {
                if (koopa != enemigo && koopa.get_limites().intersects(enemigo.get_limites())) {
                    mario.get_sistema_puntuacion().sumar_puntos(((Enemigo) enemigo).calcular_puntaje());
                    ((Enemigo) enemigo).destruir(mapa);
                }
            }
        }   
    }*/
    
    private void manejar_colision_vertical(Enemigo enemigo) {
        for (Plataforma plataforma : mapa.get_entidades_plataformas()) {
        	if (enemigo.get_limites_superiores().intersects(plataforma.get_limites_inferiores()))
        			plataforma.aceptar(enemigo);
	     }
    } 
    
    private void manejar_colision_horizontal(Enemigo entidad) {
        if (colisiona_con_plataforma(entidad.get_limites_derecha())) {
            entidad.set_direccion(-1);
        } else if (colisiona_con_plataforma(entidad.get_limites_izquierda())) {
            entidad.set_direccion(1);
        }
    }
    
    private boolean colisiona_con_plataforma(Rectangle limites) {
        return mapa.get_entidades_plataformas().stream()
            .anyMatch(plataforma -> plataforma.get_limites().intersects(limites));
    }
    
    public boolean get_murio_mario() { return murio_mario; }
    
    public void set_murio_mario(boolean murio) { murio_mario = murio; }
}