package entidades;

import entidades.interfaces.*;
import fabricas.Sprite;
import logica.*;
import java.awt.Dimension;
import java.awt.Rectangle;

public abstract class Entidad implements EntidadLogica, Agresiva, Consumible, Destruible, Gravedad {

    protected Sprite sprite;
    protected double posicion_en_x;
    protected double posicion_en_y;
    protected double velocidad_en_x, velocidad_en_y;
    protected Dimension dimension;
    protected double gravedad_aceleracion;
    protected boolean falling = true, jumping = false;
    protected Observer observer;

    protected Entidad(double x, double y, Sprite sprite) {
        this.sprite = sprite;
        this.posicion_en_x = x;
        this.posicion_en_y = y;
        if (sprite != null) {
        	set_dimension(48, 48);
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
        return new Rectangle((int) posicion_en_x + dimension.width / 6, (int) posicion_en_y, 2 * dimension.width / 3, dimension.height / 2);
    }

    public Rectangle get_limites_inferiores() {
        return new Rectangle((int) posicion_en_x + dimension.width / 6, (int) posicion_en_y + dimension.height / 2, 2 * dimension.width / 3, dimension.height / 2);
    }

    public Rectangle get_limites_izquierda() {
        return new Rectangle((int) posicion_en_x, (int) posicion_en_y + dimension.height / 4, dimension.width / 4, dimension.height / 2);
    }

    public Rectangle get_limites_derecha() {
        return new Rectangle((int) posicion_en_x + 3 * dimension.width / 4, (int) posicion_en_y + dimension.height / 4, dimension.width / 4, dimension.height / 2);
    }

    public Rectangle get_limites() {
        return new Rectangle((int) posicion_en_x, (int) posicion_en_y, dimension.width, dimension.height);
    }

    public boolean esta_cayendo() {
        return falling;
    }

    public void set_cayendo(boolean falling) {
        this.falling = falling;
    }

    public boolean esta_saltando() {
        return jumping;
    }

    public void set_saltando(boolean jumping) {
        this.jumping = jumping;
    }
}
