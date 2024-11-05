package entidades.plataformas;

public interface PlataformasVisitorMario {
	boolean visitar(BloqueDePregunta bloque_de_pregunta);
	boolean visitar(BloqueSolido bloque_solido);
	boolean visitar(LadrilloSolido ladrillo_solido);
	boolean visitar(Tuberia tuberia);
	boolean visitar(Vacio vacio);
}
