package entidades.enemigos;

public interface EnemigosVisitor {
	int visitar(BuzzyBeetle buzzy);
	int visitar(Goomba goomba);
	int visitar(KoopaTroopa koopa);
	int visitar(Lakitu lakitu);
	int visitar(PiranhaPlant piranha);
	int visitar(Spiny spiny);
}