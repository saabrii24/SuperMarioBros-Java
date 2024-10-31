package entidades.enemigos;

import logica.ResultadoColision;

public interface EnemigosVisitor {
	ResultadoColision visitar(BuzzyBeetle buzzy);
	ResultadoColision visitar(Goomba goomba);
	ResultadoColision visitar(KoopaTroopa koopa);
	ResultadoColision visitar(Lakitu lakitu);
	ResultadoColision visitar(PiranhaPlant piranha);
	ResultadoColision visitar(Spiny spiny);
}