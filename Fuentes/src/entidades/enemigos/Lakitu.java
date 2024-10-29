package entidades.enemigos;

import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

public class Lakitu extends Enemigo{
	
	private long tiempo_ultimo_spiny = 0;
	private static final long SPINY_COOLDOWN = 3000000000L;

	public Lakitu(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	public void mover() {
	}

	public void actualizar() {
        long tiempo_actual = System.nanoTime();
        if (tiempo_actual - tiempo_ultimo_spiny >= SPINY_COOLDOWN) {
            Mapa mapa = Juego.get_instancia().get_mapa_nivel_actual();
            if (mapa != null) {
                lanzar_spiny(mapa);
                tiempo_ultimo_spiny = tiempo_actual;
            }
        }
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
		Spiny spiny = new Spiny ((int) this.get_posicion_en_x() + 10, (int )this.get_posicion_en_y(), Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_izquierda());
		mapa.agregar_spiny(spiny);	
		spiny.registrar_observer(Juego.get_instancia().get_controlador_vistas().registrar_entidad(spiny));
	}

}
