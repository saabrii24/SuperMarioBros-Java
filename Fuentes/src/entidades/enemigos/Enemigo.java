package entidades.enemigos;

import entidades.EntidadMovible;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Mapa;
import logica.ResultadoColision;

public abstract class Enemigo extends EntidadMovible implements PlataformasVisitorEnemigos{
	
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public abstract int calcular_puntaje();
	public abstract int calcular_penalizacion();
	public abstract void destruir(Mapa mapa);
	public abstract ResultadoColision aceptar(EnemigosVisitor visitador);
	public abstract void visitar(Vacio vacio);
	public abstract void visitar(BloqueSolido bloque_solido);
	public abstract void visitar(BloqueDePregunta bloque_de_pregunta);
	public abstract void visitar(Tuberias tuberia);
	public abstract void visitar(LadrilloSolido ladrillo_solido);
}