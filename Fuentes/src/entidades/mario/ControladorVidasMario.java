package entidades.mario;

import entidades.interfaces.SistemaVidas;

public class ControladorVidasMario implements SistemaVidas {
	protected int vidas;

    public ControladorVidasMario(int vidas_iniciales) {
        this.vidas = vidas_iniciales;
    }

    public void sumar_vida() {
        if (esta_vivo()) vidas++;
    }

    public void quitar_vida() {
        if (esta_vivo()) vidas--;
    }

    public int get_vidas() {
        return vidas;
    }

    public boolean esta_vivo() {
        return vidas > 0;
    }
}