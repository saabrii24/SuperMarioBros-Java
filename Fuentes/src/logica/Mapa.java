package logica;

import java.util.LinkedList;
import java.util.List;
import entidades.Entidad;
import entidades.mario.Mario;

public class Mapa {
	
	protected Juego mi_juego;
	protected Mario mario;
	protected List<Entidad> entidades;
	
	public Mapa(Juego juego) {
		this.mi_juego = juego;
	    this.entidades = new LinkedList<>();
	}

    public List<Entidad> get_entidades() {
        return entidades;
    }

    public Mario get_mario() {
        return mario;
    }

    public void agregar_entidad(Entidad entidad) {
        entidades.add(entidad);
    }

    public void agregar_mario(Mario mario) {
        this.mario = mario;
    }

    public void resetear_mapa() {
        entidades.clear();
    }

    public void mover_jugador(int direccion) {
    	
    }

    public boolean nivel_completado() {

    	return false;
    }

    public void chequear_colisiones() {
        /*
        for (Entidad entidad : entidades) {
            if (entidad.matar_si_hay_colision(mario)) {
                entidad.destruir();
            }
        }
        */
    }

}
