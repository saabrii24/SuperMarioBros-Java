package entidades.mario;

import entidades.interfaces.SistemaPuntuacion;

public class ControladorPuntuacionMario implements SistemaPuntuacion {
    protected int puntaje_acumulado;
    protected int monedas;
    
    public ControladorPuntuacionMario() {
    	this.puntaje_acumulado=0;
    	this.monedas=0;
    }

    public void sumar_puntos(int puntos) {
        this.puntaje_acumulado += puntos;
    }


    public int get_puntaje_total() {
        return puntaje_acumulado;
    }

    public void sumar_moneda() {
        this.monedas++;
    }

    public int get_monedas() {
        return monedas;
    }
}