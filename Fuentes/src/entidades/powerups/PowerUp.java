package entidades.powerups;

import entidades.Entidad;
import fabricas.Sprite;
import logica.Mapa;

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
}
