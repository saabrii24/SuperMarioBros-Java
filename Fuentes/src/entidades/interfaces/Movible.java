package entidades.interfaces;

import java.awt.Rectangle;

public interface Movible {
	public void set_cayendo(boolean esta_cayendo);
	public void set_velocidad_en_y(double i);
	public void set_velocidad_en_x(double i);
	public Rectangle get_limites_superiores();
	public Rectangle get_limites_derecha();
	public Rectangle get_limites_izquierda();
	public Rectangle get_limites_inferiores();
	public Rectangle get_limites();
	public void set_direccion(int i);
}
