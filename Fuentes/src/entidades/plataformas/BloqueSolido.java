package entidades.plataformas;

import fabricas.Sprite;

public class BloqueSolido extends Plataforma{

	public BloqueSolido(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public void aceptar(PlataformasVisitorMario visitador) {
		visitador.visitar(this);
	}
	
	@Override
	public void aceptar(PlataformasVisitorEnemigos visitador) {
		visitador.visitar(this);
	}

}