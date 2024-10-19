package logica;

import entidades.mario.Mario;
import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import entidades.interfaces.Movible;

import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class Colisionador {
    private Mapa mapa;

    public Colisionador(Mapa mapa) {
        this.mapa = mapa;
    }

    // Verifica colisiones para Mario
    public synchronized void verificar_colision_mario(Mario mario) {
        manejar_colision_con_plataformas(mario);
        manejar_colision_con_enemigos(mario);
    }

    // Verifica colisiones para Enemigos
    public synchronized void verificar_colision_enemigo(Enemigo enemigo) {
        manejar_colision_vertical(enemigo);
        manejar_colision_horizontal(enemigo);
    }

    // Verifica colisiones para proyectiles (Bola de Fuego)
    public synchronized void verificar_colision_proyectil(BolaDeFuego proyectil) {
        if (colisiona_con_plataforma(proyectil.get_limites())) {
            proyectil.destruir(mapa);
        } else {
            manejar_colision_con_enemigos(proyectil);
        }
    }

    // Manejo de colisiones entre el proyectil y los enemigos
    private void manejar_colision_con_enemigos(BolaDeFuego proyectil) {
        for (Enemigo enemigo : new ArrayList<>(mapa.get_entidades_enemigo())) {
            if (proyectil.get_limites().intersects(enemigo.get_limites())) {
                enemigo.destruir(mapa);
                proyectil.destruir(mapa);
                break;
            }
        }
    }

    // Manejo de colisiones entre Mario y los enemigos
    private void manejar_colision_con_enemigos(Mario mario) {
        for (Enemigo enemigo : new ArrayList<>(mapa.get_entidades_enemigo())) {
            if (mario.get_limites_superiores().intersects(enemigo.get_limites_inferiores())) {
                enemigo.destruir(mapa);
                break;
            }
        }
    }

    // Manejo de colisiones verticales para entidades movibles
    private void manejar_colision_vertical(Movible entidad) {
        boolean colision_superior = colisiona_con_plataforma(entidad.get_limites_superiores());
        entidad.set_cayendo(!colision_superior);
        entidad.set_velocidad_en_y(colision_superior ? 0 : 5);
    }

    // Manejo de colisiones horizontales para entidades movibles
    private void manejar_colision_horizontal(Movible entidad) {
        if (colisiona_con_plataforma(entidad.get_limites_derecha())) {
            entidad.set_direccion(-1);
        } else if (colisiona_con_plataforma(entidad.get_limites_izquierda())) {
            entidad.set_direccion(1);
        }
    }

    // Manejo de colisiones entre Mario y las plataformas
    private void manejar_colision_con_plataformas(Mario mario) {
        boolean colision_derecha = colisiona_con_plataforma(mario.get_limites_derecha());
        boolean colision_izquierda = colisiona_con_plataforma(mario.get_limites_izquierda());
        boolean colision_inferior = colisiona_con_plataforma(mario.get_limites_inferiores());
        boolean colision_superior = colisiona_con_plataforma(mario.get_limites_superiores());

        manejar_colision_horizontal_mario(mario, colision_derecha, colision_izquierda);
        manejar_colision_vertical_mario(mario, colision_superior, colision_inferior);
    }

    private void manejar_colision_horizontal_mario(Mario mario, boolean colision_derecha, boolean colision_izquierda) {
        if (colision_derecha || colision_izquierda) {
            mario.bloquear_movimiento_horizontal();
            mario.set_posicion_en_x(mario.get_posicion_en_x() + (colision_derecha ? -0.5 : 0.5));
        } else {
            mario.activar_movimiento_horizontal();
        }
    }

    private void manejar_colision_vertical_mario(Mario mario, boolean colision_superior, boolean colision_inferior) {
        if (colision_superior) {
            mario.bloquear_movimiento_vertical();
            mario.set_posicion_en_y(mario.get_posicion_en_y() + 0.5);
            mario.set_contador_saltos(0);
        } else {
            mario.activar_movimiento_vertical();
        }
    }

    // Verifica si hay colisión con cualquier plataforma
    private boolean colisiona_con_plataforma(Rectangle limites) {
        for (Rectangle plataforma : obtener_limites_de_plataformas()) {
            if (plataforma.intersects(limites)) {
                return true;
            }
        }
        return false;
    }

    // Obtiene los límites de todas las plataformas en el mapa
    private List<Rectangle> obtener_limites_de_plataformas() {
        List<Rectangle> limites_plataformas = new ArrayList<>();
        synchronized (mapa.get_entidades_plataforma()) {
            for (var plataforma : mapa.get_entidades_plataforma()) {
                limites_plataformas.add(plataforma.get_limites());
            }
        }
        return limites_plataformas;
    }
}
