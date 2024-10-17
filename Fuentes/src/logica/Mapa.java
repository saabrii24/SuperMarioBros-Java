package logica;

import java.util.LinkedList;
import java.util.List;

import entidades.BolaDeFuego;
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
	protected List<BolaDeFuego> entidades_bolas_de_fuego;
	
	
	public Mapa(Juego juego) {
		this.mi_juego = juego;
	    this.entidades_enemigos = new LinkedList<>();
	    this.entidades_plataforma = new LinkedList<>();
	    this.entidades_powerup = new LinkedList<>();
	    this.entidades_bolas_de_fuego = new LinkedList<>();
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
    public List<BolaDeFuego> get_entidades_proyectiles(){
    	return entidades_bolas_de_fuego;
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
    
    public void agregar_bola_de_fuego(BolaDeFuego bolaDeFuego) {
        if (bolaDeFuego != null) {
            entidades_bolas_de_fuego.add(bolaDeFuego);
            Observer observer = mi_juego.controlador_vistas.registrar_entidad(bolaDeFuego);
            bolaDeFuego.registrar_observer(observer);
        }
    }

    public void eliminar_enemigo(Enemigo enemigo) {
    	entidades_enemigos.remove(enemigo);
    }
    public void eliminar_powerup(PowerUp powerup) {
    	entidades_powerup.remove(powerup);
    }
    public void eliminar_plataforma(Plataforma plataforma) {
    	entidades_plataforma.remove(plataforma);
    }
    public void eliminar_bola_de_fuego(BolaDeFuego bola_de_fuego) {
    	entidades_bolas_de_fuego.remove(bola_de_fuego);
    }

    public void resetear_mapa() {
        entidades_enemigos.clear();
        entidades_plataforma.clear();
        entidades_powerup.clear();
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
    
    public void reproducir_efecto(String efecto) {
    	mi_juego.reproducir_efecto(efecto);
    }
    
}