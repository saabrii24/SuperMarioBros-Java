package entidades.mario;

import entidades.enemigos.Enemigo;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.SuperChampi;

public class InvencibleMarioState implements Mario.MarioState {
    private Mario mario;
    private Mario.MarioState estado_anterior;

    public InvencibleMarioState(Mario mario) {
        this.mario = mario;
        this.estado_anterior = mario.get_estado_anterior();
    }

	public void consumir(SuperChampi super_champi) {}

	public void consumir(FlorDeFuego flor_de_fuego) {}

	public void consumir(Estrella estrella) {
		mario.actualizar_puntaje_nivel_actual(35);
	}

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        // Mario invencible elimina a los enemigos al tocarlos
        return true; // Mario mata al enemigo
    }

    public void finalizar_invulnerabilidad() {
        mario.cambiar_estado(estado_anterior); // Vuelve al estado previo al efecto de la Estrella
    }

}

