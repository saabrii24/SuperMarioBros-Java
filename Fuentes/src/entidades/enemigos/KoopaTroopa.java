package entidades.enemigos;

import fabricas.Sprite;
import logica.Mapa;

public class KoopaTroopa extends Enemigo {
	protected KoopaState estado_actual;
	
    public KoopaTroopa(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        estado_actual = new NormalKoopaState(this);
    }

    public interface KoopaState {
    	void actualizar_sprite();
    	void mover();
    	void cambiar_estado();
    	boolean en_movimiento();
    	boolean mata_tocando();
    }

    public void actualizar() {
        if (cayendo) {
            velocidad_en_y += GRAVEDAD;
            set_posicion_en_y((get_posicion_en_y() - velocidad_en_y));
        }

        set_posicion_en_x(get_posicion_en_x() + velocidad_en_x);
        actualizar_sprite();
        
    }
    
    public int calcular_puntaje() { return 90; }
    
    public int calcular_penalizacion() { return 45; }

    public void mover() {
    	estado_actual.mover();
    }

    protected void mover_izquierda() {
        this.velocidad_en_x = -3;
        movimiento_derecha = false;
    }

    protected void mover_derecha() {
        this.velocidad_en_x = 3;
        movimiento_derecha = true;
    }

    protected void detener_movimiento() {
        this.velocidad_en_x = 0;
    }
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_koopa_troopa(this);
            destruir();
        }
    }

    private void actualizar_sprite() {
    	estado_actual.actualizar_sprite();
    }
    public void cambiar_sprite(Sprite sprite) {
    	this.sprite = sprite;
    }
    public void set_estado(KoopaState estado) {
    	estado_actual = estado;
    }
    public KoopaState get_estado() {
    	return estado_actual;
    }
    public int get_direccion() {
    	return direccion;
    }
    public double get_velocidad_en_x() {
    	return velocidad_en_x;
    }
    public boolean get_movimiento_derecha() {
    	return movimiento_derecha;
    }
    public boolean en_movimiento() {
    	return estado_actual.en_movimiento();
    }
    public boolean mata_tocando() {
    	return estado_actual.mata_tocando();
    }
    public void cambiar_estado() {
    	estado_actual.cambiar_estado();
    }
    
}