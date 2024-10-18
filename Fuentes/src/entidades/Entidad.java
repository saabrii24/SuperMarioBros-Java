package entidades;

import entidades.interfaces.*;
import fabricas.Sprite;
import logica.*;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;

public abstract class Entidad extends JComponent implements EntidadLogica, Agresiva, Consumible, Destruible, Gravedad, Movible {

    private static final long serialVersionUID = 1L;

    // Campos de la clase
    protected Sprite sprite;
    protected double posicion_en_x, posicion_en_y;
    protected double velocidad_en_x, velocidad_en_y;
    protected Dimension dimension;
    protected double aceleracion_gravedad;
    protected Observer observer;
    protected boolean cayendo, saltando, movimiento_derecha;
    protected int direccion = -1;
    protected boolean destruida = false;

    // Constructor
    protected Entidad(double posicion_en_x, double posicion_en_y, Sprite sprite) {
        this.sprite = sprite;
        this.posicion_en_x = posicion_en_x;
        this.posicion_en_y = posicion_en_y;
        this.dimension = (sprite != null) ? new Dimension(sprite.get_ancho(), sprite.get_alto()) : new Dimension(0, 0);
        this.velocidad_en_x = 0;
        this.velocidad_en_y = 0;
        this.aceleracion_gravedad = 0.1;
        this.cayendo = true;
        this.saltando = false;
    }

    // Getters
    public Sprite get_sprite() {
        return sprite;
    }

    public double get_posicion_en_x() {
        return posicion_en_x;
    }

    public double get_posicion_en_y() {
        return posicion_en_y;
    }

    public double get_velocidad_en_x() {
        return velocidad_en_x;
    }

    public double get_velocidad_en_y() {
        return velocidad_en_y;
    }

    public double get_aceleracion_gravedad() {
        return aceleracion_gravedad;
    }

    public Dimension get_dimension() {
        return dimension;
    }

    public int get_direccion() {
        return direccion;
    }

    public boolean esta_cayendo() {
        return cayendo;
    }

    public boolean esta_destruida() {
        return destruida;
    }

    // Setters
    public void set_sprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void set_posicion_en_x(double posicion_x) {
        this.posicion_en_x = posicion_x;
    }

    public void set_posicion_en_y(double posicion_y) {
        this.posicion_en_y = posicion_y;
    }

    public void set_velocidad_en_x(double velocidad_x) {
        this.velocidad_en_x = velocidad_x;
    }

    public void set_velocidad_en_y(double velocidad_y) {
        this.velocidad_en_y = velocidad_y;
    }

    public void set_aceleracion_gravedad(double aceleracion_gravedad) {
        this.aceleracion_gravedad = aceleracion_gravedad;
    }

    public void set_dimension(int ancho, int alto) {
        this.dimension = new Dimension(ancho, alto);
    }

    public void set_direccion(int direccion) {
        this.direccion = direccion;
    }

    public void set_cayendo(boolean cayendo) {
        this.cayendo = cayendo;
    }

    // Métodos relacionados con el observador
    public void registrar_observer(Observer observer) {
        this.observer = observer;
    }

    public void notificar_observer() {
        if (observer != null) {
            observer.actualizar();
        }
    }

    // Métodos de movimiento
    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    private void mover_izquierda() {
        this.velocidad_en_x = -3;
        movimiento_derecha = false;
        actualizar_posicion();
    }

    private void mover_derecha() {
        this.velocidad_en_x = 3;
        movimiento_derecha = true;
        actualizar_posicion();
    }

    private void detener_movimiento() {
        this.velocidad_en_x = 0;
        actualizar_posicion();
    }

    // Métodos de colisiones
    public Rectangle get_limites_superiores() {
        return new Rectangle((int) posicion_en_x + dimension.width / 4, (int) posicion_en_y, dimension.width / 2, dimension.height / 4);
    }

    public Rectangle get_limites_inferiores() {
        return new Rectangle((int) posicion_en_x + dimension.width / 4, (int) posicion_en_y + dimension.height - dimension.height / 4, 
                             dimension.width / 2, dimension.height / 4);
    }

    public Rectangle get_limites_izquierda() {
        return new Rectangle((int) posicion_en_x, (int) posicion_en_y + dimension.height / 4, dimension.width / 8, dimension.height / 2);
    }

    public Rectangle get_limites_derecha() {
        return new Rectangle((int) posicion_en_x + 7 * dimension.width / 8, (int) posicion_en_y + dimension.height / 4, 
                             dimension.width / 8, dimension.height / 2);
    }

    public Rectangle get_limites() {
        return new Rectangle((int) posicion_en_x, (int) posicion_en_y, dimension.width - 1, dimension.height - 1);
    }

    // Métodos de actualización y lógica interna
    public void actualizar_posicion() {
        if (cayendo) {
            velocidad_en_y += aceleracion_gravedad;
            posicion_en_y -= velocidad_en_y;
        }

        if (saltando) {
            velocidad_en_y -= aceleracion_gravedad;
            posicion_en_y -= velocidad_en_y;
            if (velocidad_en_y <= 0) {
                saltando = false;
                cayendo = true;
            }
        }

        posicion_en_x += velocidad_en_x;
    }

    public void destruir() {
        destruida = true;
        if (observer != null) {
            observer.notificar_destruir();
        }
    }
}
