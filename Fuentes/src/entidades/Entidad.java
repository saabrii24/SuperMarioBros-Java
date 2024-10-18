package entidades;

import entidades.interfaces.*;
import fabricas.Sprite;
import logica.*;
import java.awt.Dimension;
import java.awt.Rectangle;

public abstract class Entidad implements EntidadLogica, Agresiva, Consumible, Destruible, Gravedad, Movible {

	protected Sprite sprite;
	protected double posicion_en_x;
	protected double posicion_en_y;
	protected double velocidad_en_x, velocidad_en_y;
	protected Dimension dimension;
	protected double gravedad_aceleracion;
	protected Observer observer;
	protected double gravedad = 0.38;
	protected boolean saltando;
	protected boolean cayendo;
	protected boolean movimiento_derecha;
	protected int direccion_entidad = -1;
	protected boolean destruida = false;  

	protected Entidad(double x, double y, Sprite sprite) {
		this.sprite = sprite;
		this.posicion_en_x = x;
		this.posicion_en_y = y;
        if (sprite != null) {
            set_dimension(sprite.get_ancho(), sprite.get_alto());
        }
		this.gravedad_aceleracion = 0.38;
		this.velocidad_en_x = 0;
		this.velocidad_en_y = 0;
	}

	public Sprite get_sprite() {
		return sprite;
	}
	
	public double get_posicion_en_x() {
		return posicion_en_x;
	}

	public double get_posicion_en_y() {
		return posicion_en_y;
	}

	public void set_posicion_en_x(double x) {
		posicion_en_x = x;
	}

	public void set_posicion_en_y(double y) {
		posicion_en_y = y;
	}

	public double get_velocidad_en_x() {
		return velocidad_en_x;
	}

	public void set_velocidad_en_x(double velocidad_en_x) {
		this.velocidad_en_x = velocidad_en_x;
	}

	public double get_velocidad_en_y() {
		return velocidad_en_y;
	}

	public void set_velocidad_en_y(double velocidad_en_y) {
		this.velocidad_en_y = velocidad_en_y;
	}

	public double get_gravedad_aceleracion() {
		return gravedad_aceleracion;
	}

	public void set_gravedad_aceleracion(double gravedad_aceleracion) {
		this.gravedad_aceleracion = gravedad_aceleracion;
	}

	public Dimension get_dimension() {
		return dimension;
	}

	public void set_dimension(int width, int height) {
		this.dimension = new Dimension(width, height);
	}

	public void registrar_observer(Observer observer) {
		this.observer = observer;
	}

	public void notificar_observer() {
		if (observer != null) {
			observer.actualizar();
		}
	}

	// Métodos para el cálculo de colisiones
	public Rectangle get_limites_superiores() {
		return new Rectangle((int) posicion_en_x + dimension.width / 6, (int) posicion_en_y, 2 * dimension.width / 3,
				dimension.height / 2);
	}

	public Rectangle get_limites_inferiores() {
		return new Rectangle((int) posicion_en_x + dimension.width / 6, (int) posicion_en_y + dimension.height / 2,
				2 * dimension.width / 3, dimension.height / 2);
	}

	public Rectangle get_limites_izquierda() {
		return new Rectangle((int) posicion_en_x, (int) posicion_en_y + dimension.height / 4, dimension.width / 4,
				dimension.height / 2);
	}

	public Rectangle get_limites_derecha() {
		return new Rectangle((int) posicion_en_x + 3 * dimension.width / 4, (int) posicion_en_y + dimension.height / 4,
				dimension.width / 4, dimension.height / 2);
	}

	public Rectangle get_limites() {
		return new Rectangle((int) posicion_en_x, (int) posicion_en_y, dimension.width+3, dimension.height+3);
	}

	public boolean esta_cayendo() {
		return cayendo;
	}

	public void set_cayendo(boolean falling) {
		this.cayendo = falling;
	}

	public boolean esta_saltando() {
		return saltando;
	}

	public void set_saltando(boolean jumping) {
		this.saltando = jumping;
	}
	
	public int get_direccion_entidad() {
		return direccion_entidad;
	}

	public void mover() {
		if (get_direccion_entidad() == 0) {
			detener_movimiento();
		}
		if (get_direccion_entidad() == 1) {
			mover_a_derecha();
		}
		if (get_direccion_entidad() == -1) {
			mover_a_izquierda();
		}
	}

	public void set_direccion_entidad(int direccion) {
		direccion_entidad = direccion;
	}

	public void set_velocidad_en_y(int gravedad) {
		velocidad_en_y = gravedad;

	}

	private void mover_a_izquierda() {
		this.velocidad_en_x = -5;
		movimiento_derecha = false;
		actualizar_posicion();
	}

	private void mover_a_derecha() {
		this.velocidad_en_x = 5;
		movimiento_derecha = true;
		actualizar_posicion();
	}

	private void detener_movimiento() {
		this.velocidad_en_x = 0;
		actualizar_posicion();
	}

	public void actualizar_posicion() {
		if (saltando) {
			velocidad_en_y -= gravedad;
			posicion_en_y -= velocidad_en_y;
		} else if (cayendo) {
			posicion_en_y -= velocidad_en_y;
		}

		posicion_en_x += velocidad_en_x;
	}

    public boolean es_destruida() {
        return destruida;
    }
    
	public void destruir() {
		destruida = true;
		observer.notificar_destruir();
	}
   
}
