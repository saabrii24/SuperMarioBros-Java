package entidades.mario;

import entidades.interfaces.SistemaPuntuacion;

public class ControladorPuntuacionMario implements SistemaPuntuacion {
    protected int puntaje_acumulado;
    protected int puntaje_nivel_actual;
    protected int monedas;

    public ControladorPuntuacionMario() {
        this.puntaje_acumulado = 0;
        this.puntaje_nivel_actual = 0;
        this.monedas = 0;
    }

    public void sumar_puntos(int puntos) {
        this.puntaje_nivel_actual += puntos;
    }
    
    public void restar_puntos(int puntos) {
        puntaje_acumulado = Math.max(puntaje_acumulado - puntos, 0);
        puntaje_nivel_actual = Math.max(puntaje_nivel_actual - puntos, 0);
    }

    public int get_puntaje_total() {
        return puntaje_acumulado;
    }

    public int get_puntaje_nivel_actual() {
        return puntaje_nivel_actual;
    }

    public void sumar_moneda() {
        this.monedas++;
    }

    public int get_monedas() {
        return monedas;
    }

    public void pasar_nivel() {
        actualizar_puntaje(puntaje_nivel_actual);
        puntaje_nivel_actual = 0;
    }

    public void actualizar_puntaje(int puntos) {
        if (puntos >= 0) { 
            this.puntaje_acumulado += puntos;
        }
    }

    public void resetear_monedas(int nivel_actual) {
        monedas = 0;
    }
    
    public void resetear_puntos(int nivel_actual) {
        puntaje_nivel_actual = 0;
    }
}
