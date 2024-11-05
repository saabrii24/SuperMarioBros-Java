package entidades.interfaces;

import entidades.enemigos.*;

public interface EnemigosVisitor {
	boolean visitar(BuzzyBeetle buzzy);
	boolean visitar(Goomba goomba);
	boolean visitar(KoopaTroopa koopa);
	boolean visitar(Lakitu lakitu);
	boolean visitar(PiranhaPlant piranha);
	boolean visitar(Spiny spiny);
}