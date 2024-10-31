package entidades.plataformas;

public interface PlataformasVisitorMario {
	void visitar(BloqueDePregunta bloque_de_pregunta);
	void visitar(BloqueSolido bloque_solido);
	void visitar(LadrilloSolido ladrillo_solido);
	void visitar(Tuberias tuberia);

}
