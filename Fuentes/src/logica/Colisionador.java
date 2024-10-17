package logica;

import entidades.mario.Mario;
import entidades.enemigos.Enemigo;
import entidades.interfaces.Movible;

import java.awt.Rectangle;

public class Colisionador {

    private Mapa mapa;

    public Colisionador(Mapa mapa) {
        this.mapa = mapa;
    }

    public void verificar_colision_mario(Mario mario) {
        manejar_colision_vertical(mario);
        manejar_colision_horizontal_mario(mario);
    }

    public void verificar_colision_enemigo(Enemigo enemigo) {
        manejar_colision_vertical(enemigo);
        manejar_colision_horizontal_enemigos(enemigo);
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
    
    private void manejar_colision_horizontal_mario(Movible mario) {
        if (colisiona_con_plataforma(mario.get_limites_derecha())) {
        	mario.set_velocidad_en_x(0);
        }

        if (colisiona_con_plataforma(mario.get_limites_izquierda())) {
        	mario.set_velocidad_en_x(0);
        }
    }

    private boolean colisiona_con_plataforma(Rectangle limites) {
        return mapa.get_entidades_plataforma().stream()
                   .anyMatch(plataforma -> plataforma.get_limites().intersects(limites));
    }
}
