package entidades.enemigos;

import entidades.EntidadMovible;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Mapa;
import logica.ResultadoColision;

public abstract class Enemigo extends EntidadMovible implements PlataformasVisitorEnemigos,EnemigoVisitorEnemigo{
	
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public abstract int calcular_puntaje();
	public abstract int calcular_penalizacion();
	public abstract void destruir(Mapa mapa);
	public abstract ResultadoColision aceptar(EnemigosVisitor visitador);
	public abstract void aceptar(EnemigoVisitorEnemigo visitador);
}