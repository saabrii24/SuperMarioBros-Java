package entidades.interfaces;

public interface SistemaPuntuacion {
    void sumar_puntos(int puntos);
	void restar_puntos(int puntos);
    int get_puntaje_total();
	int get_puntaje_nivel_actual();
    void sumar_moneda();
    int get_monedas();
	void pasar_nivel();
	void actualizar_puntaje(int puntos);
}