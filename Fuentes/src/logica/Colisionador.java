package logica;

import java.awt.Rectangle;

import entidades.Entidad;
import entidades.enemigos.Enemigo;
import entidades.mario.Mario;

public class Colisionador {

    private Mapa mapa;

    public Colisionador(Mapa mapa) {
        this.mapa = mapa;
    }

    public void verificar_colision_enemigo(Enemigo enemigo) {
        // Verificar colisiones laterales (izquierda o derecha)
        if (colisiona_con_plataforma(enemigo.get_limites_izquierda())) {
            enemigo.set_direccion_enemigo(1);; // Cambia la dirección a la derecha
        } else if (colisiona_con_plataforma(enemigo.get_limites_derecha())) {
            enemigo.set_direccion_enemigo(-1); // Cambia la dirección a la izquierda
        }
    }
    

    private boolean colisiona_con_plataforma(Rectangle limites) {
        // Itera sobre todas las plataformas del mapa y verifica si colisiona con alguna
        for (Entidad plataforma : mapa.get_entidades_plataforma()) {
            if (plataforma.get_limites().intersects(limites)) {
            	System.out.println("Detecte colision");
                return true; // Colisión detectada
            }
        }
        return false; // No hay colisión
    }
}
