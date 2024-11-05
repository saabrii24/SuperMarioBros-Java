package entidades.plataformas;

import fabricas.Sprite;

public class Tuberia extends Plataforma{

	public Tuberia(int x, int y, Sprite sprite) {
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
