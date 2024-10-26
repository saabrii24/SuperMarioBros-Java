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
import entidades.enemigos.BuzzyBeetle;
import entidades.enemigos.Enemigo;
import entidades.enemigos.Goomba;
import entidades.enemigos.KoopaTroopa;
import entidades.enemigos.Lakitu;
import entidades.enemigos.PiranhaPlant;
import entidades.enemigos.Spiny;

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
        for (BuzzyBeetle buzzy : mapa.get_entidades_buzzy_beetle()) {
        	manejar_colision_vertical(buzzy);
            manejar_colision_horizontal(buzzy);
        }
        for (Enemigo koopa : mapa.get_entidades_koopa_troopa()) {
        	manejar_colision_vertical(koopa);
            manejar_colision_horizontal(koopa);
        }
        for (Spiny spiny : mapa.get_entidades_spiny()) {
        	manejar_colision_vertical(spiny);
            manejar_colision_horizontal(spiny);
        }
        for (PiranhaPlant piranha : mapa.get_entidades_piranha_plant()) {
        	manejar_colision_vertical(piranha);
            manejar_colision_horizontal(piranha);
        }
        for (Lakitu lakitu : mapa.get_entidades_lakitu()) {
        	manejar_colision_vertical(lakitu);
            manejar_colision_horizontal(lakitu);
        }
        for (Goomba goomba : mapa.get_entidades_goomba()) {
        	manejar_colision_vertical(goomba);
            manejar_colision_horizontal(goomba);
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
        for (Goomba goomba : new ArrayList<>(mapa.get_entidades_goomba())) {
        	if (mario.mata_tocando()) {
        		if (mario.get_limites().intersects(goomba.get_limites())) {
        			mario.get_sistema_puntuacion().sumar_puntos(goomba.calcular_puntaje());
        			goomba.destruir(mapa);
        		}
        	}
            if (mario.get_limites_superiores().intersects(goomba.get_limites_inferiores())) {
            	mario.get_sistema_puntuacion().sumar_puntos(goomba.calcular_puntaje());
                goomba.destruir(mapa);
                break;
            }
            if (mario.get_limites_derecha().intersects(goomba.get_limites()) || mario.get_limites_izquierda().intersects(goomba.get_limites())) {
            	if(mario.colision_con_enemigo(goomba)) {
            		murio_mario = true;
            	}
            	else {
            		murio_mario = false;
                    mario.get_sistema_puntuacion().sumar_puntos(goomba.calcular_puntaje());
            		goomba.destruir(mapa);
            	}
            }
        }
        for (KoopaTroopa koopa : new ArrayList<>(mapa.get_entidades_koopa_troopa())) {
            if (mario.mata_tocando() && mario.get_limites().intersects(koopa.get_limites())) {
                koopa.destruir(mapa);
            }
            if (mario.get_limites_superiores().intersects(koopa.get_limites_inferiores())) {
                koopa.cambiar_estado();
                mario.set_posicion_en_y(mario.get_posicion_en_y() + 50);
                break;
            }
            if (mario.get_limites_derecha().intersects(koopa.get_limites()) || mario.get_limites_izquierda().intersects(koopa.get_limites())) {
                if (mario.colision_con_enemigo(koopa)) {
                    murio_mario = true;
                } else {
                    murio_mario = false;
                    mario.get_sistema_puntuacion().sumar_puntos(koopa.calcular_puntaje());
                    koopa.cambiar_estado();
                }
            }
        }

        // Manejo de colisiones con BuzzyBeetle
        for (BuzzyBeetle buzzy : new ArrayList<>(mapa.get_entidades_buzzy_beetle())) {
            if (mario.mata_tocando() && mario.get_limites().intersects(buzzy.get_limites())) {
            	mario.get_sistema_puntuacion().sumar_puntos(buzzy.calcular_puntaje());
                buzzy.destruir(mapa);
            }
            if (mario.get_limites_superiores().intersects(buzzy.get_limites_inferiores())) {
                buzzy.destruir(mapa);
                break;
            }
            if (mario.get_limites_derecha().intersects(buzzy.get_limites()) || mario.get_limites_izquierda().intersects(buzzy.get_limites())) {
                if (mario.colision_con_enemigo(buzzy) && !mario.mata_tocando()) {
                    murio_mario = true;
                } else {
                    murio_mario = false;
                    mario.get_sistema_puntuacion().sumar_puntos(buzzy.calcular_puntaje());
                    buzzy.destruir(mapa);
                }
            }
        }

        // Manejo de colisiones con PiranhaPlant
        for (PiranhaPlant piranha : new ArrayList<>(mapa.get_entidades_piranha_plant())) {
            if (mario.mata_tocando() && mario.get_limites().intersects(piranha.get_limites())) {
            	mario.get_sistema_puntuacion().sumar_puntos(piranha.calcular_puntaje());
                piranha.destruir(mapa);
            }
            if (mario.get_limites_superiores().intersects(piranha.get_limites_inferiores()) && !mario.mata_tocando()) {
                murio_mario=true;
                break;
            }
            if (mario.get_limites_derecha().intersects(piranha.get_limites()) || mario.get_limites_izquierda().intersects(piranha.get_limites())) {
                if (mario.colision_con_enemigo(piranha) && !mario.mata_tocando()) {
                    murio_mario = true;
                } else {
                    murio_mario = false;
                    mario.get_sistema_puntuacion().sumar_puntos(piranha.calcular_puntaje());
                    piranha.destruir(mapa);
                }
            }
        }

        // Manejo de colisiones con Lakitu
        for (Lakitu lakitu : new ArrayList<>(mapa.get_entidades_lakitu())) {
            if (mario.mata_tocando() && mario.get_limites().intersects(lakitu.get_limites())) {
            	mario.get_sistema_puntuacion().sumar_puntos(lakitu.calcular_puntaje());
                lakitu.destruir(mapa);
            }
            if (mario.get_limites_superiores().intersects(lakitu.get_limites_inferiores()) && !mario.mata_tocando()) {
                lakitu.destruir(mapa);
                break;
            }
            if (mario.get_limites_derecha().intersects(lakitu.get_limites()) || mario.get_limites_izquierda().intersects(lakitu.get_limites())) {
                if (mario.colision_con_enemigo(lakitu)) {
                    murio_mario = true;
                } else {
                    murio_mario = false;
                    mario.get_sistema_puntuacion().sumar_puntos(lakitu.calcular_puntaje());
                    lakitu.destruir(mapa);
                }
            }
        }

        // Manejo de colisiones con Spiny
        for (Spiny spiny : new ArrayList<>(mapa.get_entidades_spiny())) {
            if (mario.mata_tocando() && mario.get_limites().intersects(spiny.get_limites())) {
                mario.get_sistema_puntuacion().sumar_puntos(spiny.calcular_puntaje());
                spiny.destruir(mapa);
            }
            if (mario.get_limites_superiores().intersects(spiny.get_limites_inferiores())) {
                murio_mario = true;
                break;
            }
            if (mario.get_limites_derecha().intersects(spiny.get_limites()) || mario.get_limites_izquierda().intersects(spiny.get_limites())) {
                if (mario.colision_con_enemigo(spiny)) {
                    murio_mario = true;
                } else {
                    murio_mario = false;
                    mario.get_sistema_puntuacion().sumar_puntos(spiny.calcular_puntaje());
                    spiny.destruir(mapa);
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
        List<KoopaTroopa> koopas = new ArrayList<>(mapa.get_entidades_koopa_troopa());
        Mario mario = Mario.get_instancia();
        for (BolaDeFuego proyectil : proyectiles) {
            // Colisión con plataformas
            if (colisiona_con_plataforma(proyectil.get_limites())) {
                proyectil.destruir(mapa);
                continue;
            }

            // Colisión con enemigos
            for (Goomba goomba : new ArrayList<>(mapa.get_entidades_goomba())) {
                if (proyectil.get_limites().intersects(goomba.get_limites())) {
                    goomba.destruir();
                    mario.get_sistema_puntuacion().sumar_puntos(goomba.calcular_puntaje());
                    proyectil.destruir(mapa);
                    break;
                }
            }
            for (BuzzyBeetle buzzy : new ArrayList<>(mapa.get_entidades_buzzy_beetle())) {
                if (proyectil.get_limites().intersects(buzzy.get_limites())) {
                    buzzy.destruir();
                    mario.get_sistema_puntuacion().sumar_puntos(buzzy.calcular_puntaje());
                    proyectil.destruir(mapa);
                    break;
                }
            }
            for (KoopaTroopa koopa : new ArrayList<>(mapa.get_entidades_koopa_troopa())) {
                if (proyectil.get_limites().intersects(koopa.get_limites())) {
                    koopa.destruir(mapa);
                    mario.get_sistema_puntuacion().sumar_puntos(koopa.calcular_puntaje());
                    proyectil.destruir(mapa);
                    break;
                }
            }
            for (Lakitu lakitu : new ArrayList<>(mapa.get_entidades_lakitu())) {
                if (proyectil.get_limites().intersects(lakitu.get_limites())) {
                    lakitu.destruir(mapa);
                    mario.get_sistema_puntuacion().sumar_puntos(lakitu.calcular_puntaje());
                    proyectil.destruir(mapa);
                    break;
                }
            }
            for (PiranhaPlant piranha : new ArrayList<>(mapa.get_entidades_piranha_plant())) {
                if (proyectil.get_limites().intersects(piranha.get_limites())) {
                    piranha.destruir(mapa);
                    mario.get_sistema_puntuacion().sumar_puntos(piranha.calcular_puntaje());
                    proyectil.destruir(mapa);
                    break;
                }
            }
            for (Spiny spiny : new ArrayList<>(mapa.get_entidades_spiny())) {
                if (proyectil.get_limites().intersects(spiny.get_limites())) {
                    spiny.destruir();
                    mario.get_sistema_puntuacion().sumar_puntos(spiny.calcular_puntaje());
                    proyectil.destruir(mapa);
                    break;
                }
            }
        }
        // Colisiones con Koopa Troopa en modo proyectil
        for (KoopaTroopa koopa : koopas) {
            if (!koopa.mata_tocando()) {
                continue; // Si no está en modo proyectil, no hace nada
            }
            // Colisión con enemigos
            for (Goomba goomba : new ArrayList<>(mapa.get_entidades_goomba())) {
                if (koopa.get_limites().intersects(goomba.get_limites())) {
                	mario.get_sistema_puntuacion().sumar_puntos(goomba.calcular_puntaje());
                    goomba.destruir(mapa);
                    break;
                }
            }
            for (BuzzyBeetle buzzy : new ArrayList<>(mapa.get_entidades_buzzy_beetle())) {
                if (koopa.get_limites().intersects(buzzy.get_limites())) {
                	mario.get_sistema_puntuacion().sumar_puntos(buzzy.calcular_puntaje());
                    buzzy.destruir();
                    break;
                }
            }
            for (Lakitu lakitu : new ArrayList<>(mapa.get_entidades_lakitu())) {
                if (koopa.get_limites().intersects(lakitu.get_limites())) {
                	mario.get_sistema_puntuacion().sumar_puntos(lakitu.calcular_puntaje());
                    lakitu.destruir(mapa);
                    break;
                }
            }
            for (PiranhaPlant piranha : new ArrayList<>(mapa.get_entidades_piranha_plant())) {
                if (koopa.get_limites().intersects(piranha.get_limites())) {
                	mario.get_sistema_puntuacion().sumar_puntos(piranha.calcular_puntaje());
                    piranha.destruir(mapa);
                    break;
                }
            }
            for (Spiny spiny : new ArrayList<>(mapa.get_entidades_spiny())) {
                if (koopa.get_limites().intersects(spiny.get_limites())) {
                	mario.get_sistema_puntuacion().sumar_puntos(spiny.calcular_puntaje());
                    spiny.destruir(mapa);
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