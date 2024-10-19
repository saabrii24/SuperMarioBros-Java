package entidades.mario;

import entidades.interfaces.SistemaVidas;

public class VidasMario implements SistemaVidas {
    private int vidas;

    public VidasMario(int vidas_iniciales) {
        this.vidas = vidas_iniciales;
    }

    @Override
    public void sumar_vida() {
        if (esta_vivo()) vidas++;
    }

    @Override
    public void quitar_vida() {
        if (esta_vivo()) vidas--;
    }

    @Override
    public int get_vidas() {
        return vidas;
    }

    @Override
    public boolean esta_vivo() {
        return vidas > 0;
    }
}