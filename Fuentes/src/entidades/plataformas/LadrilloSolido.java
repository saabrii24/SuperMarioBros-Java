package entidades.plataformas;

import fabricas.Sprite;
import logica.Mapa;

@SuppressWarnings("serial")
public class LadrilloSolido extends Plataforma{

	public LadrilloSolido(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_ladrillo_solido(this);
            eliminar_del_mapa();
        }
    }
}
