
package entidades.interfaces;

public interface SistemaPuntuacion {
    void sumar_puntos(int puntos);
    void restar_puntos(int puntos);
    int get_puntaje_total();
    int get_puntaje_nivel_actual();
    void sumar_moneda();
    int get_monedas_total();
    int get_monedas_nivel_actual();
    void actualizar_puntaje(int puntos);
    void resetear_monedas(int nivel_actual);
    void resetear_puntos(int nivel_actual, int vidas);
	void pasar_nivel();
}