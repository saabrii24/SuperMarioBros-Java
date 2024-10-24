package entidades.enemigos;

import fabricas.Sprite;
import logica.Mapa;

public class PiranhaPlant extends Enemigo{

	public PiranhaPlant(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public void mover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_piranha_plant(this);
            destruir();
        }
    }

}
