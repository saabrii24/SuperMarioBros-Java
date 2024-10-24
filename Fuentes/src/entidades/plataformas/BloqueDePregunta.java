package entidades.plataformas;

import entidades.powerups.PowerUp;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

@SuppressWarnings("serial")
public class BloqueDePregunta extends Plataforma{
	private PowerUp powerup;
	int contador_powerup = 0;

	public BloqueDePregunta(int x, int y, Sprite sprite, PowerUp powerup) {
		super(x, y, sprite);
		this.powerup = powerup;
	}
	
	public void destruir(Mapa mapa) {
        	mapa.reproducir_efecto("kick");        
            this.set_sprite(Juego.get_instancia().get_fabrica_sprites().get_bloque_de_pregunta_destruido());
            contador_powerup++;
            if(contador_powerup < 2) {
            	mapa.agregar_powerup(powerup);
            	powerup.revelar();
            	observer.actualizar();
            }
           
    }
}
