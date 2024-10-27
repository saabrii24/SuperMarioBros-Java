package entidades.powerups;

import entidades.Entidad;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;
import logica.Observer;

@SuppressWarnings("serial")
public abstract class PowerUp extends Entidad{

	protected boolean envuelto;
	
	public PowerUp(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		envuelto = false;
	}
	
	public abstract void aceptar(PowerUpVisitor visitador);
	
	public void destruir(Mapa mapa) {
        if (!destruida)
        	if(!envuelto) {
	            destruida = true;           
	            mapa.eliminar_powerup(this);  
	            eliminar_del_mapa();
        	}else {
        		destruida = true;           
                mapa.eliminar_powerup(this);  
                eliminar_del_mapa();
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

	public boolean get_envuelto() {
		return envuelto;
	}
	public void set_envuelto(boolean envuelto) {
		this.envuelto = envuelto;
	}
}