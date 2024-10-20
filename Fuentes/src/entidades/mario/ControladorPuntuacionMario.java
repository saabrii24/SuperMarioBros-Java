package entidades.mario;

import entidades.interfaces.SistemaPuntuacion;

public class ControladorPuntuacionMario implements SistemaPuntuacion {
    protected int puntaje_nivel_actual;
    protected int puntaje_acumulado;
    protected int monedas;

    public void sumar_puntos(int puntos) {
        this.puntaje_nivel_actual += puntos;
    }

    public void restar_puntos(int puntos) {
        this.puntaje_acumulado -= puntos;
    }

    public int get_puntaje_total() {
        return puntaje_acumulado + puntaje_nivel_actual;
    }

    public void sumar_moneda() {
        this.monedas++;
    }

    public int get_monedas() {
        return monedas;
    }
}