package entidades.mario;

import entidades.enemigos.Enemigo;

public class FireMarioState implements Mario.MarioState {
    private Mario mario;

    public FireMarioState(Mario mario) {
        this.mario = mario;
    }
	
	public void consumir_estrella() {
		mario.cambiar_estado(new InvencibleMarioState(mario));
		mario.get_sistema_puntuacion().sumar_puntos(30);
	}

	public void consumir_super_champi() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

	public void consumir_flor_de_fuego() {
		mario.get_sistema_puntuacion().sumar_puntos(50);
	}

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        // En estado Fire Mario, si colisiona con un enemigo, vuelve al estado Normal
        mario.cambiar_estado(new NormalMarioState(mario));
        return false; // Mario no mata al enemigo, pero tampoco no pierde una vida
    }

    public void disparar_bola_de_fuego() {
        mario.disparar(); // Mario lanza una bola de fuego
    }

	@Override
	public void actualizar_sprite() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finalizar_invulnerabilidad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean mata_tocando() {
		// TODO Auto-generated method stub
		return false;
	}

}
