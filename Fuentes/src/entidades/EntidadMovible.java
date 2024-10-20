package entidades;

import entidades.interfaces.Movible;
import fabricas.Sprite;

public abstract class EntidadMovible extends Entidad implements Movible {

	protected static final double GRAVEDAD = 0.15;
    protected double velocidad_en_x, velocidad_en_y;
    protected boolean cayendo, saltando, movimiento_derecha;
    protected int direccion;

    public EntidadMovible(double posicion_en_x, double posicion_en_y, Sprite sprite) {
        super(posicion_en_x, posicion_en_y, sprite);
        this.velocidad_en_x = 0;
        this.velocidad_en_y = 0;
        this.cayendo = true;
        this.saltando = false;
        this.direccion = -1;
    }

    @Override
    public void set_velocidad(double vx, double vy) {
        set_velocidad_en_x(vx);
        set_velocidad_en_y(vy);
    }

    @Override
    public void set_velocidad_en_x(double vx) {
        this.velocidad_en_x = vx;
    }

    @Override
    public void set_velocidad_en_y(double vy) {
        this.velocidad_en_y = vy;
    }

    @Override
    public double get_velocidad_x() {
        return velocidad_en_x;
    }

    @Override
    public double get_velocidad_y() {
        return velocidad_en_y;
    }

    @Override
    public void set_cayendo(boolean esta_cayendo) {
        this.cayendo = esta_cayendo;
    }

    @Override
    public void set_direccion(int direccion) {
        this.direccion = direccion;
    }

    public void set_saltando(boolean saltando) {
        this.saltando = saltando;
    }

    public boolean esta_cayendo() {
        return cayendo;
    }

    public int get_direccion() {
        return direccion;
    }

    @Override
    public abstract void mover();
    protected abstract void actualizar_posicion();
}
