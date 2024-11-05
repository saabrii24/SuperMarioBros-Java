package entidades;

import entidades.interfaces.Movible;
import fabricas.*;

public abstract class EntidadMovible extends Entidad implements Movible {
    protected static final double GRAVEDAD = 0.3;
    protected double velocidad_en_x = 0, velocidad_en_y = 0;
    protected boolean cayendo = true, saltando = false, movimiento_derecha;
    protected int direccion = -1;
    protected SpritesFactory fabrica_sprites;

    public EntidadMovible(double posicion_en_x, double posicion_en_y, Sprite sprite) {
        super(posicion_en_x, posicion_en_y, sprite);
    }
    
    //setters
    public void set_velocidad(double vx, double vy) { set_velocidad_en_x(vx); set_velocidad_en_y(vy); }
    public void set_velocidad_en_x(double vx) { this.velocidad_en_x = vx; }
    public void set_velocidad_en_y(double vy) { this.velocidad_en_y = vy; }
    public void set_cayendo(boolean esta_cayendo) { this.cayendo = esta_cayendo; }
    public void set_direccion(int direccion) { this.direccion = direccion; }
    public void set_movimiento_derecha(boolean movimiento_derecha) { this.movimiento_derecha = movimiento_derecha; }
    public void set_saltando(boolean saltando) { this.saltando = saltando; }
    public void actualizar_fabrica_sprites(SpritesFactory nueva_fabrica) { this.fabrica_sprites = nueva_fabrica; }
    
    //getters
    public double get_velocidad_en_x() { return velocidad_en_x; }
    public double get_velocidad_en_y() { return velocidad_en_y; }
    public boolean esta_cayendo() { return cayendo; }
    public int get_direccion() { return direccion; }
    
    public abstract void mover();
    public abstract void actualizar();

   
}
