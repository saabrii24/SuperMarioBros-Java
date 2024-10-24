package entidades.enemigos;

import fabricas.Sprite;
import logica.Mapa;

public class Lakitu extends Enemigo{

	public Lakitu(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public void mover() {
		//TO DO
	}

	public void actualizar() {
		//TO DO
	}
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_lakitu(this);
            destruir();
        }
    }

}
