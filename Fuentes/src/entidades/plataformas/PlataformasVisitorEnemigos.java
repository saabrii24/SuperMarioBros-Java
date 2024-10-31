package entidades.plataformas;

public interface PlataformasVisitorEnemigos {
	void visitar(BloqueSolido bloque_solido);
	void visitar(BloqueDePregunta bloque_de_pregunta);
	void visitar(Tuberias tuberia);
	void visitar(LadrilloSolido ladrillo_solido);
	void visitar(Vacio vacio);
}
