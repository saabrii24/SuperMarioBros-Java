package entidades.mario;

import entidades.enemigos.Enemigo;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.SuperChampi;

public class SuperMarioState implements Mario.MarioState {
    private Mario mario;

    public SuperMarioState(Mario mario) {
        this.mario = mario;
    }
    
    public void actualizar_sprite() {

    }

	public void consumir(SuperChampi super_champi) {
		mario.set_puntaje_nivel_actual(50);
	}

	public void consumir(FlorDeFuego flor_de_fuego) {
		mario.set_puntaje_nivel_actual(30);
		mario.cambiar_estado(new FireMarioState(mario));
	}

	public void consumir(Estrella estrella) {
		mario.set_puntaje_nivel_actual(30);
		mario.cambiar_estado(new InvencibleMarioState(mario));
	}

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        // En estado Super Mario, si colisiona con un enemigo, vuelve al estado Normal
        mario.cambiar_estado(new NormalMarioState(mario));
        return false; // Mario no mata al enemigo, pero tampoco no pierde una vida
    }

}

