package entidades.mario;

import entidades.interfaces.SistemaVidas;
import logica.Juego;

public class ControladorVidasMario implements SistemaVidas {
	protected int vidas;

    public ControladorVidasMario(int vidas_iniciales) {
        this.vidas = vidas_iniciales;
    }

    public void sumar_vida() {
        if (esta_vivo()) vidas++;
    }

    public void quitar_vida() {
    	if(vidas > 1)
    		Juego.get_instancia().reproducir_efecto("mariodie");
    	else {
    		Juego.get_instancia().reproducir_efecto("gameover");
    	}
        if (esta_vivo()) vidas--;
    }

    public int get_vidas() {
        return vidas;
    }

    public boolean esta_vivo() {
        return vidas > 0;
    }
}