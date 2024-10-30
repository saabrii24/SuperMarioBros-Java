package entidades.plataformas;

import entidades.Entidad;
import fabricas.Sprite;

public abstract class Plataforma extends Entidad {
	
	public Plataforma(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public boolean aceptar(PlataformasVisitorEnemigos visitador) {
		return visitador.visitar(this);
	}

}
