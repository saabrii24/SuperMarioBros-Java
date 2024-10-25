package entidades.enemigos;

import fabricas.Sprite;
import fabricas.SpritesFactory;
import logica.Juego;
import logica.Mapa;

public class Spiny extends Enemigo{

	public Spiny(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		set_velocidad_en_x(3);
	}

	public void actualizar() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }

        posicion_en_x += velocidad_en_x;
        actualizar_sprite();
    }
	
    public int calcular_puntaje() {
        return 60;
    }
    
    public int calcular_penalizacion() {
        return 30;
    }

    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    protected void mover_izquierda() {
        this.velocidad_en_x = -2;
        movimiento_derecha = false;
    }

    protected void mover_derecha() {
        this.velocidad_en_x = 2;
        movimiento_derecha = true;
    }

    protected void detener_movimiento() {
        this.velocidad_en_x = 0;
    }
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_spiny(this);
            destruir();
        }
    }

	private void actualizar_sprite() {
		if (velocidad_en_x != 0) {
            set_sprite(movimiento_derecha ? 
            	Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_derecha() : 
            	Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_izquierda());
		}
	}

}
