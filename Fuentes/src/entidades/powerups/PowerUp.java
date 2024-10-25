package entidades.powerups;

import entidades.Entidad;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;
import logica.Observer;

public abstract class PowerUp extends Entidad{

	private static final long serialVersionUID = 1L;
	
	public PowerUp(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	public abstract void aceptar(PowerUpVisitor visitador);
	
	public void destruir(Mapa mapa) {
        if (!destruida) {
            destruida = true;           
            mapa.eliminar_powerup(this);  
            destruir();
        }
    }

	public void actualizar() {
		
	}

	public void revelar() {
		Juego.get_instancia().reproducir_efecto("powerup_appears");
		this.set_posicion_en_y(this.get_posicion_en_y() + 48);
		observer.actualizar();	
	}

	public Observer get_observer() {
		return observer;
	}
}
