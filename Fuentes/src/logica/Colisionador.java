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
        manejarColisionConPlataforma(mario);
    }

    public void verificar_colision_enemigo(Enemigo enemigo) {
        manejarColisionVertical(enemigo);
        manejarColisionHorizontal(enemigo);
    }

    public void verificar_colision_proyectil(BolaDeFuego proyectil) {
        if (colisionaConPlataforma(proyectil.get_limites())) {
            proyectil.destruir(mapa);
        } else {
            manejarColisionConEnemigos(proyectil);
        }
    }

    private void manejarColisionConEnemigos(BolaDeFuego proyectil) {
        mapa.get_entidades_enemigo().stream()
            .filter(enemigo -> proyectil.get_limites().intersects(enemigo.get_limites()))
            .findFirst()
            .ifPresent(enemigo -> {
                enemigo.destruir(mapa);
                proyectil.destruir(mapa);
            });
    }

    private void manejarColisionVertical(Movible entidad) {
        boolean colisionSuperior = colisionaConPlataforma(entidad.get_limites_superiores());
        entidad.set_cayendo(!colisionSuperior);
        entidad.set_velocidad_en_y(colisionSuperior ? 0 : 5);
    }

    private void manejarColisionHorizontal(Movible entidad) {
        if (colisionaConPlataforma(entidad.get_limites_derecha())) {
            entidad.set_direccion_entidad(-1);
        } else if (colisionaConPlataforma(entidad.get_limites_izquierda())) {
            entidad.set_direccion_entidad(1);
        }
    }

    private void manejarColisionConPlataforma(Mario mario) {
        boolean colisionDerecha = colisionaConPlataforma(mario.get_limites_derecha());
        boolean colisionIzquierda = colisionaConPlataforma(mario.get_limites_izquierda());
        boolean colisionInferior = colisionaConPlataforma(mario.get_limites_superiores());

        if (colisionDerecha || colisionIzquierda) {
            mario.bloquear_movimiento();
            mario.set_posicion_en_x(mario.get_posicion_en_x() + (colisionDerecha ? -0.5 : 0.5));
        } else {
            mario.activar_movimiento();
        }

        mario.set_cayendo(!colisionInferior);
        if (colisionInferior) {
            mario.set_velocidad_en_y(0);
        }
    }

    private boolean colisionaConPlataforma(Rectangle limites) {
        return mapa.get_entidades_plataforma().stream()
                   .anyMatch(plataforma -> plataforma.get_limites().intersects(limites));
    }
}
