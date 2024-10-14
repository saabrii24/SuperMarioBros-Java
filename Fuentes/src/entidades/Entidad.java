package entidades;

import entidades.interfaces.*;
import fabricas.Sprite;
import logica.*;

public abstract class Entidad implements EntidadLogica, Agresiva, Consumible, Destruible, Gravedad{
	
	protected Sprite sprite;
	protected double posicion_en_x;
	protected double posicion_en_y;
	protected Observer observer;
	
	protected Entidad(double x, double y, Sprite sprite) {
		this.sprite = sprite;
		posicion_en_x = x;
		posicion_en_y = y;
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
	
	public void set_posicion_en_x(int x) {
		posicion_en_x = x;
	}
	
	public void set_posicion_en_y(int y) {
		posicion_en_y = y;
	}
	
	public void registrar_observer(Observer observer) {
		this.observer = observer;
	}
	
    public void notificar_observer() {
            observer.actualizar();
    }
}