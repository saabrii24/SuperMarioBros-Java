package logica;

import java.util.LinkedList;
import java.util.List;
import entidades.enemigos.Enemigo;
import entidades.mario.Mario;
import entidades.plataformas.Plataforma;
import entidades.powerups.PowerUp;

public class Mapa {
	
	protected Juego mi_juego;
	protected Mario mario;
	protected List<Enemigo> entidades_enemigos;
	protected List<PowerUp> entidades_powerup;
	protected List<Plataforma> entidades_plataforma;
	
	
	public Mapa(Juego juego) {
		this.mi_juego = juego;
	    this.entidades_enemigos = new LinkedList<>();
	    this.entidades_plataforma = new LinkedList<>();
	    this.entidades_powerup = new LinkedList<>();
	    
	}

    public List<Plataforma> get_entidades_plataforma() {
        return entidades_plataforma;
    }
    public List<Enemigo> get_entidades_enemigo(){
    	return entidades_enemigos;
    }
    public List<PowerUp> get_entidades_powerup(){
    	return entidades_powerup;
    }
    
    public Mario get_mario() {
        return mario;
    }

    public void agregar_mario(Mario mario) {
        this.mario = mario;
    }
    
    public void agregar_enemigo(Enemigo enemigo) {
    	entidades_enemigos.add(enemigo);
    }
    public void agregar_powerup(PowerUp powerup) {
    	entidades_powerup.add(powerup);
    }
    public void agregar_plataforma(Plataforma plataforma) {
    	entidades_plataforma.add(plataforma);
    }
    
    public void resetear_mapa() {
        entidades_enemigos.clear();
        entidades_plataforma.clear();
        entidades_powerup.clear();
        
    }

    public void mover_jugador(int direccion) {
        if (mario == null) {
            return;
        }
        switch (direccion) {
            case Juego.IZQUIERDA:
                mario.mover_a_izquierda();
                break;
            case Juego.DERECHA:
                mario.mover_a_derecha();
                break;
            case Juego.SALTAR:
                mario.saltar();
                break;
            case Juego.DISPARAR:
                mario.disparar();
                break;
            default:
                break;
        }
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

   // public void eliminar_entidad(Entidad entidad_a_eliminar) {
    //    entidades.remove(entidad_a_eliminar);
    //}
    
    public void actualizar_entidades() {
        if (mario != null) {
            mario.actualizar_posicion();
        }
        chequear_colisiones();
    }

    
}
