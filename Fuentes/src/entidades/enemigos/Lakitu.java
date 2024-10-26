package entidades.enemigos;

import entidades.mario.Mario;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

public class Lakitu extends Enemigo{

	public Lakitu(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public void mover() {
		lanzar_spinys(Juego.get_instancia().get_mapa_nivel_actual());
	}
	private void lanzar_spinys(Mapa mapa) {
		mapa.agregar_spiny(new Spiny((int) this.get_posicion_en_x() + 10, (int) this.get_posicion_en_y(), Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_izquierda()));
		observer.actualizar();
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
            destruir();
        }
    }

	public void lanzar_spiny(Mapa mapa) {
		Spiny spiny = new Spiny ((int) this.get_posicion_en_x() + 10, (int )this.get_posicion_en_y(), Mario.get_instancia().get_sprite_factory().get_spiny_movimiento_izquierda());
		mapa.agregar_spiny(spiny);	
		spiny.registrar_observer(Juego.get_instancia().get_controlador_vistas().registrar_entidad(spiny));
	}

}
