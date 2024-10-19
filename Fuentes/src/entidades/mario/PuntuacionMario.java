package entidades.mario;

import entidades.interfaces.SistemaPuntuacion;

public class PuntuacionMario implements SistemaPuntuacion {
    private int puntaje_nivel_actual;
    private int puntaje_acumulado;
    private int monedas;

    @Override
    public void sumar_puntos(int puntos) {
        this.puntaje_nivel_actual += puntos;
    }

    @Override
    public void restar_puntos(int puntos) {
        this.puntaje_acumulado -= puntos;
    }

    @Override
    public int get_puntaje_total() {
        return puntaje_acumulado + puntaje_nivel_actual;
    }

    @Override
    public void sumar_moneda() {
        this.monedas++;
    }

    @Override
    public int get_monedas() {
        return monedas;
    }
}