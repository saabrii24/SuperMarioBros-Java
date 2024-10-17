package entidades.mario;

import entidades.enemigos.Enemigo;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.SuperChampi;

public class NormalMarioState implements Mario.MarioState {
    private Mario mario;

    public NormalMarioState(Mario mario) {
        this.mario = mario;
    }

    public void consumir(SuperChampi super_champi) {
    	mario.actualizar_puntaje_nivel_actual(10);
    	mario.cambiar_estado(new SuperMarioState(mario));
    }
    public void consumir(FlorDeFuego flor_de_fuego) {
    	mario.actualizar_puntaje_nivel_actual(5);
    	mario.cambiar_estado(new FireMarioState(mario));
    }

    public void consumir(Estrella estrella) {
    	mario.actualizar_puntaje_nivel_actual(20);
    	mario.cambiar_estado(new InvencibleMarioState(mario));
    }

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        // En estado normal, Mario muere si colisiona con un enemigo
        mario.quitar_vida();
        mario.cambiar_estado(new NormalMarioState(mario));
        return false; // Mario no mata al enemigo
    }
}

