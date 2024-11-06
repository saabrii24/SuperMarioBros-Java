package entidades.interfaces;

import entidades.plataformas.*;

public interface PlataformasVisitorEnemigos {
	void visitar(BloqueSolido bloque_solido);
	void visitar(BloqueDePregunta bloque_de_pregunta);
	void visitar(Tuberia tuberia);
	void visitar(LadrilloSolido ladrillo_solido);
	void visitar(Vacio vacio);
}
