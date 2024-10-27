package entidades.enemigos;

import entidades.EntidadMovible;
import fabricas.Sprite;
import logica.Mapa;

@SuppressWarnings("serial")
public abstract class Enemigo extends EntidadMovible{
	
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public abstract int calcular_puntaje();
	public abstract int calcular_penalizacion();
	public abstract void destruir(Mapa mapa);
	
}
