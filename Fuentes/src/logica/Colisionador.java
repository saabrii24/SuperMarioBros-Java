package logica;

import entidades.mario.Mario;
import entidades.plataformas.BloqueDePregunta;
import entidades.plataformas.BloqueSolido;
import entidades.plataformas.LadrilloSolido;
import entidades.plataformas.Tuberias;
import entidades.plataformas.Vacio;
import entidades.powerups.PowerUp;
import entidades.BolaDeFuego;
import entidades.EntidadMovible;
import entidades.enemigos.Enemigo;

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
        
        // Verificar todas las colisiones en orden de prioridad
        verificar_colisiones_con_plataformas(mario);
        verificar_colisiones_con_enemigos(mario);
        verificar_colisiones_con_powerups(mario);
        verificar_colisiones_proyectiles();
    }

    private void verificar_colisiones_con_plataformas(Mario mario) {
        // Colisiones verticales
    	verificar_colision_bajo_plataformas(mario);
        verificar_colision_sobre_plataformas(mario);
        
        // Colisiones horizontales
        verificar_colision_horizontal_con_plataformas(mario);
        
        // Colisiones de Enemigos con plataformas
        for (Enemigo enemigo : mapa.get_entidades_enemigo()) {
        	manejar_colision_vertical(enemigo);
            manejar_colision_horizontal(enemigo);
        }
    }

    private void verificar_colision_bajo_plataformas(Mario mario) {
        Rectangle limites_inferiores = mario.get_limites_inferiores();
        boolean colision_detectada = false;

        for (BloqueSolido bloque_solido : mapa.get_entidades_bloque_solido()) {
            if (limites_inferiores.intersects(bloque_solido.get_limites_superiores())) {
                colision_detectada = true;
                mario.set_posicion_en_y(bloque_solido.get_posicion_en_y() - mario.get_dimension().height + 1);
                mario.set_cayendo(true);
                mario.set_velocidad_en_y(0);
                break;
            }
        }
        for (BloqueDePregunta pregunta : mapa.get_entidades_bloque_de_pregunta()) {
            if (limites_inferiores.intersects(pregunta.get_limites_superiores())) {
                colision_detectada = true;
                mario.set_posicion_en_y(pregunta.get_posicion_en_y() - mario.get_dimension().height + 1);
                mario.set_cayendo(true);
                mario.set_velocidad_en_y(0);
                pregunta.destruir(mapa);
                break;
            }
        }
        for (LadrilloSolido ladrillo : mapa.get_entidades_ladrillo_solido()) {
            if (limites_inferiores.intersects(ladrillo.get_limites_superiores())) {
                colision_detectada = true;
                mario.set_posicion_en_y(ladrillo.get_posicion_en_y() - mario.get_dimension().height + 1);
                mario.set_cayendo(true);
                mario.set_velocidad_en_y(0);
                if (mario.rompe_bloque()) {
                    ladrillo.destruir(mapa);
                }
                break;
            }
        }
        
        

        if (!colision_detectada && !mario.esta_saltando()) {
            mario.set_cayendo(true);
        }
    }

    private void verificar_colision_sobre_plataformas(Mario mario) {
        Rectangle limites_superiores = mario.get_limites_superiores();
        
        for (BloqueSolido bloque_solido : mapa.get_entidades_bloque_solido()) {
            if (limites_superiores.intersects(bloque_solido.get_limites_inferiores())) {
            	mario.set_contador_saltos(0);
                mario.set_velocidad_en_y(0);  
                mario.set_posicion_en_y(bloque_solido.get_posicion_en_y() + bloque_solido.get_dimension().height);
                break;
            }
        }
        for (BloqueDePregunta pregunta : mapa.get_entidades_bloque_de_pregunta()) {
            if (limites_superiores.intersects(pregunta.get_limites_inferiores())) {
            	mario.set_contador_saltos(0);
                mario.set_velocidad_en_y(0);  
                mario.set_posicion_en_y(pregunta.get_posicion_en_y() + pregunta.get_dimension().height);
                break;
            }
        }
        for (LadrilloSolido ladrillo_solido : mapa.get_entidades_ladrillo_solido()) {
            if (limites_superiores.intersects(ladrillo_solido.get_limites_inferiores())) {
            	mario.set_contador_saltos(0);
                mario.set_velocidad_en_y(0);  
                mario.set_posicion_en_y(ladrillo_solido.get_posicion_en_y() + ladrillo_solido.get_dimension().height);
                break;
            }
        }
        
        for (Tuberias tuberia : mapa.get_entidades_tuberias()) {
            if (limites_superiores.intersects(tuberia.get_limites_inferiores())) {
            	mario.set_contador_saltos(0);
                mario.set_velocidad_en_y(0);  
                mario.set_posicion_en_y(tuberia.get_posicion_en_y() + tuberia.get_dimension().height);
                break;
            }
        }
        for (Vacio vacio : mapa.get_entidades_vacio()) {
            if (limites_superiores.intersects(vacio.get_limites_inferiores())) {
            	mario.set_cayendo(true);
            	murio_mario=true;
            	mario.caer_en_vacio();
            
            }
        }
    }

    private void verificar_colision_horizontal_con_plataformas(Mario mario) {
        boolean hacia_derecha = mario.get_movimiento_derecha();
        Rectangle limites_laterales = hacia_derecha ? 
            mario.get_limites_derecha() : mario.get_limites_izquierda();

        for (BloqueSolido bloque_solido : mapa.get_entidades_bloque_solido()) {
            Rectangle limites_plataforma = !hacia_derecha ? 
                bloque_solido.get_limites_derecha() : bloque_solido.get_limites_izquierda();

            if (limites_laterales.intersects(limites_plataforma)) {
                mario.set_velocidad_en_x(0);
                if (hacia_derecha) {
                    mario.set_posicion_en_x(bloque_solido.get_posicion_en_x() - mario.get_dimension().width);
                } else {
                    mario.set_posicion_en_x(bloque_solido.get_posicion_en_x() + bloque_solido.get_dimension().width);
                }
                break;
            }
        }
        for (BloqueDePregunta pregunta : mapa.get_entidades_bloque_de_pregunta()) {
            Rectangle limites_plataforma = !hacia_derecha ? 
                pregunta.get_limites_derecha() : pregunta.get_limites_izquierda();

            if (limites_laterales.intersects(limites_plataforma)) {
                mario.set_velocidad_en_x(0);
                if (hacia_derecha) {
                    mario.set_posicion_en_x(pregunta.get_posicion_en_x() - mario.get_dimension().width);
                } else {
                    mario.set_posicion_en_x(pregunta.get_posicion_en_x() + pregunta.get_dimension().width);
                }
                break;
            }
        }
        for (LadrilloSolido ladrillo_solido : mapa.get_entidades_ladrillo_solido()) {
            Rectangle limites_plataforma = !hacia_derecha ? 
                ladrillo_solido.get_limites_derecha() : ladrillo_solido.get_limites_izquierda();

            if (limites_laterales.intersects(limites_plataforma)) {
                mario.set_velocidad_en_x(0);
                if (hacia_derecha) {
                    mario.set_posicion_en_x(ladrillo_solido.get_posicion_en_x() - mario.get_dimension().width);
                } else {
                    mario.set_posicion_en_x(ladrillo_solido.get_posicion_en_x() + ladrillo_solido.get_dimension().width);
                }
                break;
            }
        }
        for (Tuberias tuberia : mapa.get_entidades_tuberias()) {
            Rectangle limites_plataforma = !hacia_derecha ? 
                tuberia.get_limites_derecha() : tuberia.get_limites_izquierda();

            if (limites_laterales.intersects(limites_plataforma)) {
                mario.set_velocidad_en_x(0);
                if (hacia_derecha) {
                    mario.set_posicion_en_x(tuberia.get_posicion_en_x() - mario.get_dimension().width);
                } else {
                    mario.set_posicion_en_x(tuberia.get_posicion_en_x() + tuberia.get_dimension().width);
                }
                break;
            }
        }
        for (Vacio vacio : mapa.get_entidades_vacio()) {
            Rectangle limites_plataforma = !hacia_derecha ? 
                vacio.get_limites_derecha() : vacio.get_limites_izquierda();

            if (limites_laterales.intersects(limites_plataforma)) {
                mario.set_velocidad_en_x(0);
                if (hacia_derecha) {
                    mario.set_posicion_en_x(vacio.get_posicion_en_x() - mario.get_dimension().width);
                } else {
                    mario.set_posicion_en_x(vacio.get_posicion_en_x() + vacio.get_dimension().width);
                }
                break;
            }
        }
    }
    

    private void verificar_colisiones_con_enemigos(Mario mario) {
        for (Enemigo enemigo : new ArrayList<>(mapa.get_entidades_enemigo())) {
        	if (mario.mata_tocando()) {
        		if (mario.get_limites().intersects(enemigo.get_limites())) {
        			enemigo.destruir(mapa);
        		}
        	}
            if (mario.get_limites_superiores().intersects(enemigo.get_limites_inferiores())) {
                enemigo.destruir(mapa);
                break;
            }
            if (mario.get_limites_derecha().intersects(enemigo.get_limites()) || mario.get_limites_izquierda().intersects(enemigo.get_limites())) {
            	if(mario.colision_con_enemigo(enemigo)) {
            		murio_mario = true;
            	}
            	else {
            		murio_mario = false;
            		enemigo.destruir(mapa);
            	}
            }
        }
    }

    private void verificar_colisiones_con_powerups(Mario mario) {
    	List<PowerUp> entidades_power_up= new ArrayList<>(mapa.get_entidades_powerup());
		for(PowerUp power_up:entidades_power_up) {
			if( mario.get_limites().intersects(power_up.get_limites())) {
				power_up.aceptar(mario);
				power_up.destruir(mapa);
			}
		}
    }

    private void verificar_colisiones_proyectiles() {
        List<BolaDeFuego> proyectiles = new ArrayList<>(mapa.get_entidades_proyectiles());
        for (BolaDeFuego proyectil : proyectiles) {
            // Colisión con plataformas
            if (colisiona_con_plataforma(proyectil.get_limites())) {
                proyectil.destruir(mapa);
                continue;
            }

            // Colisión con enemigos
            for (Enemigo enemigo : new ArrayList<>(mapa.get_entidades_enemigo())) {
                if (proyectil.get_limites().intersects(enemigo.get_limites())) {
                    enemigo.destruir(mapa);
                    proyectil.destruir(mapa);
                    break;
                }
            }
        }
    }
    
    // Manejo de colisiones verticales para entidades movibles
    private void manejar_colision_vertical(EntidadMovible entidad) {
        boolean colision_superior = colisiona_con_plataforma(entidad.get_limites_superiores());
        entidad.set_cayendo(!colision_superior);
        entidad.set_velocidad_en_y(colision_superior ? 0 : 5);
    }

    // Manejo de colisiones horizontales para entidades movibles
    private void manejar_colision_horizontal(EntidadMovible entidad) {
        if (colisiona_con_plataforma(entidad.get_limites_derecha())) {
            entidad.set_direccion(-1);
        } else if (colisiona_con_plataforma(entidad.get_limites_izquierda())) {
            entidad.set_direccion(1);
        }
    }

    private boolean colisiona_con_plataforma(Rectangle limites) {
        for (BloqueSolido bloque_solido : mapa.get_entidades_bloque_solido()) {
            if (bloque_solido.get_limites().intersects(limites)) {
                return true;
            }
        }
        for (BloqueDePregunta pregunta : mapa.get_entidades_bloque_de_pregunta()) {
            if (pregunta.get_limites().intersects(limites)) {
                return true;
            }
        }
        for (LadrilloSolido ladrillo_solido : mapa.get_entidades_ladrillo_solido()) {
            if (ladrillo_solido.get_limites().intersects(limites)) {
                return true;
            }
        }
        for (Tuberias tuberia : mapa.get_entidades_tuberias()) {
            if (tuberia.get_limites().intersects(limites)) {
                return true;
            }
        
        }
        return false;
    }

    public boolean get_murio_mario() { return murio_mario; }    
    public void set_murio_mario(boolean murio) { murio_mario = murio; }
}