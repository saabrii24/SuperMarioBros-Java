package entidades.interfaces;

public interface Movible {
    void mover();
    void set_velocidad(double vx, double vy);
	void set_velocidad_en_y(double vy);
	void set_velocidad_en_x(double vx);
	double get_velocidad_x();
    double get_velocidad_y();
	void set_cayendo(boolean esta_cayendo);
	void set_direccion(int i);
}
