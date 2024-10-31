package entidades.plataformas;

import fabricas.Sprite;

public class Vacio extends Plataforma {

	public Vacio(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public void aceptar(PlataformasVisitorMario visitador) {}
	
	@Override
	public void aceptar(PlataformasVisitorEnemigos visitador) {
		visitador.visitar(this);
	}
}
