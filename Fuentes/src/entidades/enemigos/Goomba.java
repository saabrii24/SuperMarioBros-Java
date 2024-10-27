package entidades.enemigos;

import fabricas.Sprite;
import logica.Mapa;

public class Goomba extends Enemigo {
    public Goomba(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public void actualizar() {
        if (esta_cayendo()) {
            set_velocidad_en_y(get_velocidad_en_y() + GRAVEDAD);
            set_posicion_en_y(get_posicion_en_y() - get_velocidad_en_y());
        }
        set_posicion_en_x(get_posicion_en_x() + get_velocidad_en_x());
    }
    
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_goomba(this);
            eliminar_del_mapa();
        }
    }

    public int calcular_puntaje() { return 60; }

    public int calcular_penalizacion() { return 30; }

    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    protected void mover_izquierda() {
        set_velocidad_en_x(-1);
        movimiento_derecha = false;
    }

    protected void mover_derecha() {
        set_velocidad_en_x(1);
        set_movimiento_derecha(true);
        movimiento_derecha = true;
    }

    protected void detener_movimiento() {
        set_velocidad_en_x(0);
    }
   
}

