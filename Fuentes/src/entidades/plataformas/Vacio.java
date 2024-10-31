package entidades.plataformas;

import fabricas.Sprite;

public class Vacio extends Plataforma {

	public Vacio(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public boolean aceptar(PlataformasVisitorMario visitador) {
		return visitador.visitar(this);
	}
	
	@Override
	public void aceptar(PlataformasVisitorEnemigos visitador) {
		visitador.visitar(this);
	}
}
