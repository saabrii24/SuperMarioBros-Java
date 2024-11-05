package entidades.interfaces;

import entidades.enemigos.*;

public interface EnemigoVisitorEnemigo {
	void visitar_enemigo(Goomba goomba);
	void visitar_enemigo(BuzzyBeetle buzzy);
	void visitar_enemigo(KoopaTroopa koopa);
	void visitar_enemigo(Lakitu lakitu);
	void visitar_enemigo(PiranhaPlant piranha);
	void visitar_enemigo(Spiny spiny);
}