package entidades.plataformas;

import entidades.interfaces.PlataformasVisitorEnemigos;
import entidades.interfaces.PlataformasVisitorMario;
import fabricas.Sprite;

public class Vacio extends Plataforma {

	public Vacio(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public boolean aceptar(PlataformasVisitorMario visitante) {
		return visitante.visitar(this);
	}
	
	@Override
	public void aceptar(PlataformasVisitorEnemigos visitante) {
		visitante.visitar(this);
	}
}
