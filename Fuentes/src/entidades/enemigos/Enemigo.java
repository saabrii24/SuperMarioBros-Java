package entidades.enemigos;

import entidades.Entidad;
import fabricas.Sprite;

public class Enemigo extends Entidad{
	protected int direccion_enemigo = -1;
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	public int get_direccion_enemigo() {
		return direccion_enemigo;
	}
	public void set_direccion_enemigo(int direccion) {
		direccion_enemigo = direccion;
	}
}
