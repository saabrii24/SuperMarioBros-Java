package entidades.plataformas;

import entidades.interfaces.PlataformasVisitorEnemigos;
import entidades.interfaces.PlataformasVisitorMario;
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
            mapa.eliminar_plataforma(this);
            eliminar_del_mapa();
        }
    }
	
	public boolean aceptar(PlataformasVisitorMario visitante) {
		return visitante.visitar(this);
	}
	
	@Override
	public void aceptar(PlataformasVisitorEnemigos visitante) {
		visitante.visitar(this);
	}
}
