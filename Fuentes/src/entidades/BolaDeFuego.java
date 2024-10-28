package entidades;

import entidades.mario.Mario;
import fabricas.Sprite;
import logica.Mapa;

public class BolaDeFuego extends EntidadMovible {
	protected int direccion_mario;
	
    public BolaDeFuego(double x, double y, Sprite sprite) {
        super(x, y, sprite);
        direccion_mario = Mario.get_instancia().get_direccion_mario();
        this.set_direccion(direccion_mario);
        this.set_velocidad_en_x(direccion_mario == 1 ? 10 : -10);
        set_cayendo(false);
        set_saltando(false);
    }

	public void mover() {
        this.notificar_observer();
    }
    
    public void destruir(Mapa mapa) {
        if (!destruida) {
            destruida = true;           
            mapa.eliminar_bola_de_fuego(this);  
            eliminar_del_mapa();
        }
    }

    public void actualizar() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }

        if (saltando) {
            velocidad_en_y -= GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
            if (velocidad_en_y <= 0) {
                saltando = false;
                cayendo = true;
            }
        }

        posicion_en_x += velocidad_en_x;
    }
}

