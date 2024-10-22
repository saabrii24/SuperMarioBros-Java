package entidades.plataformas;

import entidades.Entidad;
import fabricas.Sprite;
import logica.Mapa;

public abstract class Plataforma extends Entidad{
	
	public Plataforma(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_plataforma(this);
            destruir();
        }
    }
	
}
