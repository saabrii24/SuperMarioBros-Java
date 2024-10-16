package entidades.enemigos;

import entidades.Entidad;
import fabricas.Sprite;

public class Enemigo extends Entidad{
	private double velocidad_en_x;
    private double velocidad_en_y;
    private double gravedad = 0.38;
    private boolean saltando;
    private boolean cayendo;
    private boolean movimiento_derecha;
    
	protected int direccion_enemigo = -1;
	public Enemigo(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		this.velocidad_en_x  = 0;
		this.velocidad_en_y = 0;
		this.cayendo = true;
		this.saltando = false;
		this.movimiento_derecha = true;
	}
	public int get_direccion_enemigo() {
		return direccion_enemigo;
	}
	public void mover() {
		if (direccion_enemigo == 0) {
			detener_movimiento();
		}
		if(direccion_enemigo == 1) {
			mover_a_derecha();
		}
		if(direccion_enemigo == -1) {
			mover_a_izquierda();
		}
	}
	public void set_direccion_enemigo(int direccion) {
		direccion_enemigo = direccion;
	}
	 private void mover_a_izquierda() {
	        this.velocidad_en_x = -10;
	        movimiento_derecha = false;
	        actualizar_posicion();
	    }

	    private void mover_a_derecha() {
	        this.velocidad_en_x = 10;
	        movimiento_derecha = true;
	        actualizar_posicion();
	    }

	    private void detener_movimiento() {
	        this.velocidad_en_x = 0; 
	        actualizar_posicion();
	    }
	    
	    public void actualizar_posicion() {
	        if (saltando && velocidad_en_y <= 0) {
	            saltando = false;
	            cayendo = true;
	        } else if (saltando) {
	            velocidad_en_y -= gravedad;
	            posicion_en_y -= velocidad_en_y;
	        }

	        posicion_en_x += velocidad_en_x;
	    } 
	
}
