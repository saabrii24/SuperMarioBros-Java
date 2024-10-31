package entidades.plataformas;

import fabricas.Sprite;
import logica.Mapa;

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
	
	public void aceptar(PlataformasVisitorMario visitador) {
		visitador.visitar(this);
	}
	
	@Override
	public void aceptar(PlataformasVisitorEnemigos visitador) {
		visitador.visitar(this);
	}
}
