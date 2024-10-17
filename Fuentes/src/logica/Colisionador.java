package logica;

import java.awt.Rectangle;

import entidades.enemigos.Enemigo;

public class Colisionador {

    private Mapa mapa;

    public Colisionador(Mapa mapa) {
        this.mapa = mapa;
    }

    public void verificar_colision_enemigo(Enemigo enemigo) {
        if (colisiona_con_plataforma(enemigo.get_limites_izquierda())) {
            enemigo.set_direccion_enemigo(1); 
            mapa.reproducir_efecto("kick");
        } else if (colisiona_con_plataforma(enemigo.get_limites_derecha())) {
            enemigo.set_direccion_enemigo(-1);
            mapa.reproducir_efecto("kick");
        }
        if (!colisiona_con_plataforma(enemigo.get_limites_superiores())) {
            enemigo.set_velocidad_en_y(5); // Aplica gravedad
            //System.out.println("Cayendo");
        } else {
        	enemigo.set_cayendo(false);
            enemigo.set_velocidad_en_y(0); // Detiene la caída si está en una plataforma
            
            //System.out.println("Deje de caer xd");
        }
    }
    

    private boolean colisiona_con_plataforma(Rectangle limites) {
        // Utiliza stream para verificar si alguna plataforma colisiona con los límites
        boolean colisiona = mapa.get_entidades_plataforma().stream().anyMatch(plataforma -> plataforma.get_limites().intersects(limites));

        if (colisiona) {
            //System.out.println("Detecte colision");
        }

        return colisiona; // Devuelve el resultado de la colisión
    }

}