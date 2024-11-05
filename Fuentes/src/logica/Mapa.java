package logica;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entidades.BolaDeFuego;
import entidades.EntidadMovible;
import entidades.enemigos.*;
import entidades.mario.Mario;
import entidades.plataformas.*;
import entidades.powerups.PowerUp;
import fabricas.SpritesFactory;

public class Mapa {

    protected Juego mi_juego;
    protected Mario mario;
    
    protected List<Enemigo> entidades_enemigos;	
    protected List<Plataforma> entidades_plataformas;
    protected List<BolaDeFuego> entidades_proyectiles;
    protected List<PowerUp> entidades_powerup;
    protected Colisionador colisionador;

    public Mapa(Juego juego) {
        this.mi_juego = juego;
        this.entidades_enemigos = new CopyOnWriteArrayList<>();
        this.entidades_plataformas = new CopyOnWriteArrayList<>();
        this.entidades_proyectiles = new CopyOnWriteArrayList<>();
        this.entidades_powerup = new CopyOnWriteArrayList<>();
        this.colisionador = new Colisionador(this);
    }

    //actualizar
    public void actualizar_entidades_movibles() {
        for (EntidadMovible entidad : get_entidades_movibles()) {
            entidad.actualizar();
        }
        colisionador.verificar_colisiones(mi_juego);
    }
    public void actualizar_fabrica_sprites(SpritesFactory nueva_fabrica) {
        for(Enemigo enemigo: entidades_enemigos) {
        	enemigo.actualizar_fabrica_sprites(nueva_fabrica);
        }
    }
    
    //getters
    private List<EntidadMovible> get_entidades_movibles() {
        List<EntidadMovible> entidades_movibles = new CopyOnWriteArrayList<>();
        entidades_movibles.addAll(entidades_enemigos);
        entidades_movibles.addAll(entidades_proyectiles);
        return entidades_movibles;
    }
    public Colisionador get_colisionador() { return colisionador; }
    public List<Enemigo> get_entidades_enemigos() { return entidades_enemigos; }
    public List<Plataforma> get_entidades_plataformas() { return entidades_plataformas; }
    public List<BolaDeFuego> get_entidades_proyectiles() { return entidades_proyectiles; }
    public List<PowerUp> get_entidades_powerup() { return entidades_powerup; }

    //Agregar al mapa
    public void agregar_mario(Mario mario) { this.mario = mario; }
    public void agregar_powerup(PowerUp powerup) { 
    	if(powerup.get_envuelto()) {
	        entidades_powerup.add(powerup);
	        Observer observer = Juego.get_instancia().controlador_vistas.registrar_entidad(powerup);
	        powerup.registrar_observer(observer);
    	} else entidades_powerup.add(powerup);
    }
    public void agregar_bola_de_fuego(BolaDeFuego bola_de_fuego) {
        if (bola_de_fuego != null) {
            entidades_proyectiles.add(bola_de_fuego);
            Observer observer = mi_juego.controlador_vistas.registrar_entidad(bola_de_fuego);
            reproducir_efecto("fireball");
            bola_de_fuego.registrar_observer(observer);
        }
    }
    public void agregar_enemigo(Enemigo enemigo) {entidades_enemigos.add(enemigo);}
    public void agregar_plataforma(Plataforma plataforma) {entidades_plataformas.add(plataforma);}
    
    //borrar del mapa
    public void eliminar_enemigo(Enemigo enemigo) { entidades_enemigos.remove(enemigo); }
    public void eliminar_powerup(PowerUp powerup) { entidades_powerup.remove(powerup); }
    public void eliminar_plataforma(Plataforma plataforma) { entidades_plataformas.remove(plataforma); }
    public void eliminar_bola_de_fuego(BolaDeFuego bola_de_fuego) { entidades_proyectiles.remove(bola_de_fuego); }
    public void barrer_mapa() {
        for (EntidadMovible entidad : get_entidades_movibles()) {
            entidad.eliminar_del_mapa();
        }
        entidades_enemigos.clear();
        for (PowerUp powerup : entidades_powerup) powerup.eliminar_del_mapa(); 
        entidades_powerup.clear();
        for (Plataforma plataformas : entidades_plataformas) plataformas.eliminar_del_mapa(); 
        entidades_plataformas.clear();
        for (BolaDeFuego proyectil : entidades_proyectiles) proyectil.eliminar_del_mapa(); 
        entidades_proyectiles.clear();
        
        if (mario != null) {
        	mario.eliminar_del_mapa();
        	mario = null;
        }
    }

    public void reproducir_efecto(String efecto) { mi_juego.reproducir_efecto(efecto); }

}