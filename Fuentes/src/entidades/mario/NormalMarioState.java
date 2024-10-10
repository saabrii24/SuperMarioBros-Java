package entidades.mario;

import entidades.enemigos.Enemigo;
import entidades.mario.Mario.MarioState;
import entidades.powerups.PowerUp;

public class NormalMarioState implements MarioState {
    private Mario mario;

    public NormalMarioState(Mario mario) {
        this.mario = mario;
    }

    @Override
    public void consumir(PowerUp powerup) {
        // lógica de consumo de power-up
    }

    @Override
    public boolean matar_si_hay_colision(Enemigo enemigo) {
        // lógica de colisión con enemigo
        return false;
    }
}
