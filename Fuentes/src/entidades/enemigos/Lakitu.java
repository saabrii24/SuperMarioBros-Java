package entidades.enemigos;

import entidades.mario.Mario;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

@SuppressWarnings("serial")
public class Lakitu extends Enemigo{

	public Lakitu(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public void mover() {
	}

	public void actualizar() {
	}
	
    public int calcular_puntaje() {
        return 60;
    }

    public int calcular_penalizacion() {
        return 0;
    }
    
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_lakitu(this);
            eliminar_del_mapa();
        }
    }

	public void lanzar_spiny(Mapa mapa) {
		if(mapa.get_entidades_spiny().size() < 10) {
			Spiny spiny = new Spiny ((int) this.get_posicion_en_x() + 10, (int )this.get_posicion_en_y(), Mario.get_instancia().get_sprite_factory().get_spiny_movimiento_izquierda());
			mapa.agregar_spiny(spiny);	
			spiny.registrar_observer(Juego.get_instancia().get_controlador_vistas().registrar_entidad(spiny));
		}
	}

}
