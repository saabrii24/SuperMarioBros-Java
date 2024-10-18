package logica;

import entidades.mario.Mario;
import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import entidades.interfaces.Movible;

import java.awt.Rectangle;

public class Colisionador {

    private Mapa mapa;

    public Colisionador(Mapa mapa) {
        this.mapa = mapa;
    }

    public void verificar_colision_mario(Mario mario) {
        //manejar_colision_vertical(mario);
        manejar_colision_horizontal_mario(mario);
    }

    public void verificar_colision_enemigo(Enemigo enemigo) {
        manejar_colision_vertical(enemigo);
        manejar_colision_horizontal_enemigos(enemigo);
    }

    public void verificar_colision_proyectil(BolaDeFuego proyectil) {
        if (colisiona_con_plataforma(proyectil.get_limites())) {
            proyectil.destruir(mapa); 
        } else {
            manejar_colision_con_enemigos(proyectil);
        }
    }

    private void manejar_colision_con_enemigos(BolaDeFuego proyectil) {
        for (Enemigo enemigo : mapa.get_entidades_enemigo()) {
            if (proyectil.get_limites().intersects(enemigo.get_limites())) {
                enemigo.destruir(mapa); 
                proyectil.destruir(mapa); 
                break; 
            }
        }
    }

    private void manejar_colision_vertical(Movible entidad) {
        if (colisiona_con_plataforma(entidad.get_limites_superiores())) {
            entidad.set_cayendo(false);
            entidad.set_velocidad_en_y(0); // Detiene la caída si está sobre una plataforma
        } else {
            entidad.set_cayendo(true);
            entidad.set_velocidad_en_y(5); // Aplica gravedad si no hay colisión
        }
    }

    private void manejar_colision_horizontal_enemigos(Movible entidad) {
        if (colisiona_con_plataforma(entidad.get_limites_derecha())) {
            entidad.set_direccion_entidad(-1);
        }

        if (colisiona_con_plataforma(entidad.get_limites_izquierda())) {
            entidad.set_direccion_entidad(1);
        }
    }

    private void manejar_colision_horizontal_mario(Mario mario) {
        boolean colision_derecha = colisiona_con_plataforma(mario.get_limites_derecha());
        boolean colision_izquierda = colisiona_con_plataforma(mario.get_limites_izquierda());
        boolean colision_inferior = colisiona_con_plataforma(mario.get_limites_superiores());

        if (colision_derecha) {
            mario.bloquear_movimiento();
            mario.set_posicion_en_x(mario.get_posicion_en_x() - 0.5);
        } else if (colision_izquierda) {
            mario.bloquear_movimiento();
            mario.set_posicion_en_x(mario.get_posicion_en_x() + 0.5);
        } else {
            mario.activar_movimiento();
        }
        if(colision_inferior) {
        	mario.set_cayendo(false);
            mario.set_velocidad_en_y(0);
            mario.mover();
        }
    }



    private boolean colisiona_con_plataforma(Rectangle limites) {
        return mapa.get_entidades_plataforma().stream()
                   .anyMatch(plataforma -> plataforma.get_limites().intersects(limites));
    }
}
