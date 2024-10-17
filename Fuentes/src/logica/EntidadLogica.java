package logica;

import java.awt.Dimension;

import fabricas.Sprite;

public interface EntidadLogica {
	public Sprite get_sprite();
	public double get_posicion_en_x();
	public double get_posicion_en_y();
	public Dimension get_dimension();
	public void set_dimension(int width, int height);
	
}
