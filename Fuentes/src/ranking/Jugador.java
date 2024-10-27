package ranking;

import java.io.Serializable;

public class Jugador implements Comparable<Jugador>, Serializable{
	private static final long serialVersionUID = 2557974393300832482L;
	private String nombre;
	private Integer puntos;
	
	public Jugador(String nombre, int puntos) {
		this.nombre = nombre;
		this.puntos = puntos;
	}
	
	public String get_nombre() {
		return nombre;
	}
	
	public Integer get_puntos() {
		return puntos;
	}

	@Override
	public int compareTo(Jugador o) {
		return this.puntos.compareTo(o.get_puntos());
	}
}
