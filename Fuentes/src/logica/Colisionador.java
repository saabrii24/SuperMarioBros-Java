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
        verificar_colision_bajo_plataformas(mario);
        verificar_colision_sobre_plataformas(mario);
        verificar_colision_horizontal_con_plataformas(mario);
        verificar_colisiones_plataformas_enemigos();
    }
    /*
     * colision enemigos plataforma ta
     * colision mario enemigos ta
     * colision mario pu ta
     * 
     * 
     * colision mario plataforma no ta aun
     * lista generales(borrar obtener_todos_enemigos y obtener_todo_plataformas)
     * 
     */
    
    //este ya esta, borrar obtener_todos_enemigos
    private void verificar_colisiones_plataformas_enemigos() {
        List<Enemigo> enemigos = obtener_todos_enemigos();
        for (Enemigo enemigo : enemigos) {
            manejar_colision_vertical(enemigo);
            manejar_colision_horizontal(enemigo);
        }
    }

    //Omitimos a piraña plant y lakitu porque no necesita detectar colisiones con plataformas
    private List<Enemigo> obtener_todos_enemigos() {
        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.addAll(mapa.get_entidades_buzzy_beetle());
        enemigos.addAll(mapa.get_entidades_koopa_troopa());
        enemigos.addAll(mapa.get_entidades_spiny());
        enemigos.addAll(mapa.get_entidades_goomba());
        enemigos.addAll(mapa.get_entidades_piranha_plant());
        enemigos.addAll(mapa.get_entidades_lakitu());
        return enemigos;
    }

    private void verificar_colision_bajo_plataformas(Mario mario) {
        Rectangle limites_inferiores = mario.get_limites_inferiores();
        boolean colision_detectada = false;

        for (BloqueSolido bloque_solido : mapa.get_entidades_bloque_solido()) {
            if (limites_inferiores.intersects(bloque_solido.get_limites_superiores())) {
                colision_detectada = true;
                ajustar_posicion_mario_bajo_plataforma(mario, bloque_solido);
                break;
            }
        }
        for (BloqueDePregunta pregunta : mapa.get_entidades_bloque_de_pregunta()) {
            if (limites_inferiores.intersects(pregunta.get_limites_superiores())) {
                colision_detectada = true;
                ajustar_posicion_mario_bajo_plataforma(mario, pregunta);
                if(mario.get_contador_saltos() == 1) pregunta.destruir(mapa);
                break;
            }
        }
        for (LadrilloSolido ladrillo : mapa.get_entidades_ladrillo_solido()) {
            if (limites_inferiores.intersects(ladrillo.get_limites_superiores())) {
                colision_detectada = true;
                ajustar_posicion_mario_bajo_plataforma(mario, ladrillo);
                if (mario.rompe_bloque() && mario.get_contador_saltos() == 1) ladrillo.destruir(mapa); 
                break;
            }
        }

        if (!colision_detectada && !mario.esta_saltando()) {
            mario.set_cayendo(true);
        }
    }

    private void ajustar_posicion_mario_bajo_plataforma(Mario mario, Plataforma plataforma) {
        mario.set_posicion_en_y(plataforma.get_posicion_en_y() - mario.get_dimension().height + 1);
        mario.set_cayendo(true);
        mario.set_velocidad_en_y(0);
	}

	private void verificar_colision_sobre_plataformas(Mario mario) {
        Rectangle limites_superiores = mario.get_limites_superiores();
        List<Plataforma> plataformas = obtener_todas_plataformas();

        for (Plataforma plataforma : plataformas) {
            if (limites_superiores.intersects(plataforma.get_limites_inferiores())) {
            	ajustar_posicion_mario_sobre_plataforma(mario, plataforma);
                break;
            }
        }
    }
	
    private void ajustar_posicion_mario_sobre_plataforma(Mario mario, Entidad plataforma) {
        mario.set_contador_saltos(0);
        mario.set_velocidad_en_y(0);
        mario.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
    }
    
    private List<Plataforma> obtener_todas_plataformas() {
        List<Plataforma> plataformas = new ArrayList<>();
        plataformas.addAll(mapa.get_entidades_bloque_solido());
        plataformas.addAll(mapa.get_entidades_bloque_de_pregunta());
        plataformas.addAll(mapa.get_entidades_ladrillo_solido());
        plataformas.addAll(mapa.get_entidades_tuberias());
        return plataformas;
    }

    private void verificar_colision_horizontal_con_plataformas(Mario mario) {
        boolean hacia_derecha = mario.get_movimiento_derecha();
        Rectangle limites_laterales = hacia_derecha ? mario.get_limites_derecha() : mario.get_limites_izquierda();

        for (Entidad plataforma : obtener_todas_plataformas()) {
            Rectangle limites_plataforma = !hacia_derecha ? 
                plataforma.get_limites_derecha() : plataforma.get_limites_izquierda();

            if (limites_laterales.intersects(limites_plataforma)) {
                ajustar_posicion_mario_horizontal(mario, plataforma, hacia_derecha);
                break;
            }
        }
    }
    
    private void ajustar_posicion_mario_horizontal(Mario mario, Entidad plataforma, boolean hacia_derecha) {
        mario.set_velocidad_en_x(0);
        if (hacia_derecha) {
            mario.set_posicion_en_x(plataforma.get_posicion_en_x() - mario.get_dimension().width);
        } else {
            mario.set_posicion_en_x(plataforma.get_posicion_en_x() + plataforma.get_dimension().width);
        }
	}

    //este ya esta, borrar obtener_todos_enemigos
    private void verificar_colisiones_con_enemigos(Mario mario) {
    	int valor=0;
    	for(Enemigo enemigo: obtener_todos_enemigos()) {
    		if(mario.get_limites().intersects(enemigo.get_limites())) {
    			valor=enemigo.aceptar(mario);
    			if(valor==1) {
    				mario.get_sistema_puntuacion().sumar_puntos(enemigo.calcular_puntaje());
    				enemigo.destruir(mapa);
    			}
    			else
    				if(valor==-1) {
    					murio_mario=mario.colision_con_enemigo(enemigo);
    				}
    		}
    	}
    }
    //este ya esta
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
        verificar_colisiones_koopa_proyectil();
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
        for (EntidadMovible enemigo : obtener_todos_enemigos()) {
            if (proyectil.get_limites().intersects(enemigo.get_limites())) {
                ((Enemigo) enemigo).destruir(mapa);
                Mario.get_instancia().get_sistema_puntuacion().sumar_puntos(((Enemigo) enemigo).calcular_puntaje());
                proyectil.destruir(mapa);
                break;
            }
        }
        for (PiranhaPlant piranha : new ArrayList<>(mapa.get_entidades_piranha_plant())) {
            if (proyectil.get_limites().intersects(piranha.get_limites())) {
                Mario.get_instancia().get_sistema_puntuacion().sumar_puntos(piranha.calcular_puntaje());
                piranha.destruir(mapa);
                proyectil.destruir(mapa);
                break;
            }
        }
    }
    //leerlo bien y ver que hacer
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
    }
    //este ya esta, borrar obtener_todas_plataformas
    private void manejar_colision_vertical(Enemigo enemigo) {
        Rectangle limites_inferiores = enemigo.get_limites_superiores();

        for (Plataforma plataforma : obtener_todas_plataformas()) {
        	if(plataforma.aceptar(enemigo)) {
	            if (limites_inferiores.intersects(plataforma.get_limites_inferiores())) {
	                ajustar_posicion_enemigo_sobre_plataforma(enemigo, plataforma);
	                break;
	            }
	        }
	     }
    }
    //este no se toca
    private void ajustar_posicion_enemigo_sobre_plataforma(EntidadMovible enemigo, Entidad plataforma) {
        enemigo.set_velocidad_en_y(0);
        enemigo.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
    }

    //este ya esta
    private void manejar_colision_horizontal(Enemigo entidad) {
        if (colisiona_con_plataforma(entidad.get_limites_derecha())) {
            entidad.set_direccion(-1);
        } else if (colisiona_con_plataforma(entidad.get_limites_izquierda())) {
            entidad.set_direccion(1);
        }
    }
    //este no se toca,por ahora
    private boolean colisiona_con_plataforma(Rectangle limites) {
        return obtener_todas_plataformas().stream()
            .anyMatch(plataforma -> plataforma.get_limites().intersects(limites));
    }
    //no se toca
    public boolean get_murio_mario() { return murio_mario; }    
    public void set_murio_mario(boolean murio) { murio_mario = murio; }
}