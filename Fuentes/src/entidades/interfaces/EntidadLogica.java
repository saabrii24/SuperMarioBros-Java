package entidades.interfaces;

import java.awt.Dimension;
import java.awt.Rectangle;

import fabricas.Sprite;
import fabricas.SpritesFactory;

public interface EntidadLogica {
	Sprite get_sprite();
	double get_posicion_en_x();
	double get_posicion_en_y();
    void set_posicion(double x, double y);
	Dimension get_dimension();
	void set_dimension(int width, int height);	
	Rectangle get_limites();
	Rectangle get_limites_inferiores();
	Rectangle get_limites_superiores();
	Rectangle get_limites_derecha();
	Rectangle get_limites_izquierda();
}
