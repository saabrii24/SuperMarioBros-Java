package logica;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import entidades.mario.Mario;
import entidades.plataformas.Plataforma;
import entidades.powerups.PowerUp;

public class Mapa {
	
	protected Juego mi_juego;
	protected Mario mario;
	protected List<Enemigo> entidades_enemigo;
	protected List<PowerUp> entidades_powerup;
	protected List<Plataforma> entidades_plataforma;
	protected List<BolaDeFuego> entidades_proyectiles;
	
	
	public Mapa(Juego juego) {
		this.mi_juego = juego;
        this.entidades_enemigo = new CopyOnWriteArrayList<>();
        this.entidades_powerup = new CopyOnWriteArrayList<>();
        this.entidades_plataforma = new CopyOnWriteArrayList<>();
        this.entidades_proyectiles = new CopyOnWriteArrayList<>();
	}

    public List<Enemigo> get_entidades_enemigo() {
        return entidades_enemigo;
    }

    public List<PowerUp> get_entidades_powerup() {
        return entidades_powerup;
    }

    public List<Plataforma> get_entidades_plataforma() {
        return entidades_plataforma;
    }

    public List<BolaDeFuego> get_entidades_proyectiles() {
        return entidades_proyectiles;
    }
    
    public void agregar_mario(Mario mario) {
        this.mario = mario;
    }
    
    public void agregar_enemigo(Enemigo enemigo) {
    	entidades_enemigo.add(enemigo);
    }
    
    public void agregar_powerup(PowerUp powerup) {
    	entidades_powerup.add(powerup);
    }
    
    public void agregar_plataforma(Plataforma plataforma) {
    	entidades_plataforma.add(plataforma);
    }
    
    public void agregar_bola_de_fuego(BolaDeFuego bola_de_fuego) {
        if (bola_de_fuego != null) {
            entidades_proyectiles.add(bola_de_fuego);
            Observer observer = mi_juego.controlador_vistas.registrar_entidad(bola_de_fuego);
            reproducir_efecto("fireball");
            bola_de_fuego.registrar_observer(observer);
        }
    }

    public void eliminar_enemigo(Enemigo enemigo) {
    	entidades_enemigo.remove(enemigo);
    }
    public void eliminar_powerup(PowerUp powerup) {
    	entidades_powerup.remove(powerup);
    }
    public void eliminar_plataforma(Plataforma plataforma) {
    	entidades_plataforma.remove(plataforma);
    }
    public void eliminar_bola_de_fuego(BolaDeFuego bola_de_fuego) {
    	entidades_proyectiles.remove(bola_de_fuego);
    }
    
    public void resetear_mapa() {
        for (Enemigo enemigo : entidades_enemigo) enemigo.destruir();
        	entidades_enemigo.clear();
        
        for (Plataforma plataforma : entidades_plataforma) plataforma.destruir();
        	entidades_plataforma.clear();
        
        for (PowerUp powerup : entidades_powerup) powerup.destruir();
        	entidades_powerup.clear();
        
        for (BolaDeFuego bolaDeFuego : entidades_proyectiles) bolaDeFuego.destruir();
        	entidades_proyectiles.clear();
        
        if (mario != null) mario.destruir();
        
        

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