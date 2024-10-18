package logica;

import java.awt.Dimension;
import java.awt.Rectangle;

import fabricas.Sprite;

public interface EntidadLogica {
	public Sprite get_sprite();
	public double get_posicion_en_x();
	public double get_posicion_en_y();
	public Dimension get_dimension();
	public void set_dimension(int width, int height);
	
	public Rectangle get_limites();
	public Rectangle get_limites_superiores();
	public Rectangle get_limites_inferiores();
	public Rectangle get_limites_izquierda();
	public Rectangle get_limites_derecha();
	
}
