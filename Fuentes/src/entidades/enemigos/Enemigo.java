package entidades.enemigos;

import entidades.EntidadMovible;
import entidades.plataformas.Plataforma;
import entidades.plataformas.PlataformasVisitorEnemigos;
import fabricas.Sprite;
import logica.Mapa;

public abstract class Enemigo extends EntidadMovible implements PlataformasVisitorEnemigos{
	
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public abstract int calcular_puntaje();
	public abstract int calcular_penalizacion();
	public abstract void destruir(Mapa mapa);
	public abstract int aceptar(EnemigosVisitor visitador);
	public abstract boolean visitar(Plataforma plataforma);
	
}