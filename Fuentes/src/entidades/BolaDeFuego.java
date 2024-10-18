package entidades;

import entidades.mario.Mario;
import fabricas.Sprite;
import logica.Mapa;

public class BolaDeFuego extends Entidad {
	protected int direccion_mario;
	
    public BolaDeFuego(double x, double y, Sprite sprite) {
        super(x, y, sprite);
        direccion_mario = Mario.get_instancia().get_direccion_mario();
        this.set_direccion(direccion_mario);
        this.set_velocidad_en_x(direccion_mario == 1 ? 10 : -10);
    }

    public void mover() {
        this.actualizar_posicion();
        this.notificar_observer();
    }
    
    public void destruir(Mapa mapa) {
        if (!destruida) {
            destruida = true;           
            mapa.eliminar_bola_de_fuego(this);  
            destruir();
        }
    }
}

