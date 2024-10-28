package entidades.interfaces;

import java.awt.Dimension;
import java.awt.Rectangle;

import fabricas.Sprite;

public interface EntidadLogica {
	double get_posicion_en_x();
	double get_posicion_en_y();
	Sprite get_sprite();
	Dimension get_dimension();
	Rectangle get_limites();
	Rectangle get_limites_inferiores();
	Rectangle get_limites_superiores();
	Rectangle get_limites_derecha();
	Rectangle get_limites_izquierda();
    void set_posicion(double x, double y);
	void set_dimension(int ancho, int alto);	
}
