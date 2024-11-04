package entidades;

import fabricas.Sprite;
import logica.Observer;

import java.awt.Dimension;
import java.awt.Rectangle;

import entidades.interfaces.EntidadLogica;

public abstract class Entidad implements EntidadLogica {
    protected Sprite sprite;
    protected double posicion_en_x, posicion_en_y;
    protected Dimension dimension;
    protected Observer observer;
    protected boolean destruida = false;

    protected Entidad(double posicion_en_x, double posicion_en_y, Sprite sprite) {
        this.sprite = sprite;
        this.posicion_en_x = posicion_en_x;
        this.posicion_en_y = posicion_en_y;
        this.dimension = (sprite != null) ? new Dimension(sprite.get_ancho(), sprite.get_alto()) : new Dimension(0, 0);
    }

    // Getters básicos
    public Sprite get_sprite() { return sprite; }
    public double get_posicion_en_x() { return posicion_en_x; }
    public double get_posicion_en_y() { return posicion_en_y; }
    public Dimension get_dimension() { return dimension; }
    public boolean esta_destruida() { return destruida; }

    // Setters básicos
    public void set_sprite(Sprite sprite) { this.sprite = sprite; }
    public void set_posicion(double x, double y) {
        set_posicion_en_x(x);
        set_posicion_en_y(y);
    }
    public void set_posicion_en_x(double x) { this.posicion_en_x = x; }
    public void set_posicion_en_y(double y) { this.posicion_en_y = y; }
    public void set_dimension(int ancho, int alto) {
        this.dimension = new Dimension(ancho, alto);
    }

    // Métodos de Observer
    public void registrar_observer(Observer observer) {
        this.observer = observer;
    }
    public void notificar_observer() {
        if (observer != null) observer.actualizar();
    }

    // Métodos de la hitbox
    public Rectangle get_limites() {
        return new Rectangle(
            (int)posicion_en_x, 
            (int)posicion_en_y, 
            dimension.width - 1, 
            dimension.height - 1
        );
    }  
	public Rectangle get_limites_inferiores() {
	    return new Rectangle(
	        (int)posicion_en_x + dimension.width / 8,
	        (int)posicion_en_y + dimension.height - dimension.height / 4,
	        dimension.width * 3/4,
	        dimension.height / 4
	    );
	}
	public Rectangle get_limites_superiores() {
	    return new Rectangle(
	        (int)posicion_en_x + dimension.width / 8,
	        (int)posicion_en_y,
	        dimension.width * 3/4,
	        dimension.height / 4
	    );
	}
	public Rectangle get_limites_izquierda() {
	    return new Rectangle(
	        (int)posicion_en_x,
	        (int)posicion_en_y + dimension.height / 4,
	        dimension.width / 4,
	        dimension.height / 2
	    );
	}
	public Rectangle get_limites_derecha() {
	    return new Rectangle(
	        (int)posicion_en_x + 3 * dimension.width / 4, 
	        (int)posicion_en_y + dimension.height / 4,
	        dimension.width / 4, 
	        dimension.height / 2
	    );
	}

	// Para borrar la entidad del mapa
    public void eliminar_del_mapa() {
        destruida = true;
        if (observer != null) {
            observer.notificar_eliminar_del_mapa();
        }
    }

}