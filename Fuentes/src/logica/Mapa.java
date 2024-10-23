package logica;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entidades.BolaDeFuego;
import entidades.enemigos.Enemigo;
import entidades.mario.Mario;
import entidades.plataformas.BloqueDePregunta;
import entidades.plataformas.BloqueSolido;
import entidades.plataformas.LadrilloSolido;
import entidades.plataformas.Plataforma;
import entidades.plataformas.Tuberias;
import entidades.plataformas.Vacio;
import entidades.powerups.PowerUp;

public class Mapa {
	
	protected Juego mi_juego;
	protected Mario mario;
	protected List<Enemigo> entidades_enemigo;
	protected List<PowerUp> entidades_powerup;
	protected List<BloqueSolido> entidades_bloque_solido;
	protected List<BloqueDePregunta> entidades_bloque_de_pregunta;
	protected List <LadrilloSolido> entidades_ladrillo_solido;
	protected List <Tuberias> entidades_tuberias;
	protected List <Vacio> entidades_vacio;
	protected List<BolaDeFuego> entidades_proyectiles;
	protected Colisionador colisionador;
	
	
	public Mapa(Juego juego) {
		this.mi_juego = juego;
        this.entidades_enemigo = new CopyOnWriteArrayList<>();
        this.entidades_powerup = new CopyOnWriteArrayList<>();
        this.entidades_bloque_solido = new CopyOnWriteArrayList<>();
        this.entidades_bloque_de_pregunta = new CopyOnWriteArrayList<>();
        this.entidades_ladrillo_solido = new CopyOnWriteArrayList<>();
        this.entidades_tuberias = new CopyOnWriteArrayList<>();
        this.entidades_vacio = new CopyOnWriteArrayList<>();
        this.entidades_proyectiles = new CopyOnWriteArrayList<>();
        this.colisionador = new Colisionador(this);
	}
	
    public void actualizar_entidades() {
    
        for (Enemigo enemigo : entidades_enemigo) {
            enemigo.actualizar();
        }
        for (PowerUp powerup : entidades_powerup) {
            powerup.actualizar();
        }
        for (BloqueSolido bloque_solido : entidades_bloque_solido) {
            bloque_solido.actualizar();
        }
        
        for (BloqueDePregunta bloque_pregunta : entidades_bloque_de_pregunta) {
        	bloque_pregunta.actualizar();
        }
        for(LadrilloSolido ladrillo_solido : entidades_ladrillo_solido) {
        	ladrillo_solido.actualizar();
        }
        for(Tuberias tuberia : entidades_tuberias) {
        	tuberia.actualizar();
        }
        for(Vacio vacio : entidades_vacio) {
        	vacio.actualizar();
        }
        for (BolaDeFuego proyectil : entidades_proyectiles) {
            proyectil.actualizar();
        }

        colisionador.verificar_colisiones(mi_juego);
    }
    
	public Colisionador get_colisionador() { return colisionador; }
	
    public List<Enemigo> get_entidades_enemigo() {
        return entidades_enemigo;
    }

    public List<PowerUp> get_entidades_powerup() {
        return entidades_powerup;
    }

    public List<BloqueSolido> get_entidades_bloque_solido() {
        return entidades_bloque_solido;
    }
    
    public List<BloqueDePregunta> get_entidades_bloque_de_pregunta(){
    	return entidades_bloque_de_pregunta;
    }
    
    public List<LadrilloSolido> get_entidades_ladrillo_solido(){
    	return entidades_ladrillo_solido;
    }
    public List<Tuberias> get_entidades_tuberias(){
    	return entidades_tuberias;
    }
    
    public List<Vacio> get_entidades_vacio(){
    	return entidades_vacio;
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
    	Observer observer = Juego.get_instancia().controlador_vistas.registrar_entidad(powerup);
    	powerup.registrar_observer(observer);
    }
    
    public void agregar_bloque_solido(BloqueSolido solido) {
    	entidades_bloque_solido.add(solido);
    }
    public void agregar_bloque_de_pregunta (BloqueDePregunta pregunta) {
    	entidades_bloque_de_pregunta.add(pregunta);
    }
    public void agregar_ladrillo_solido (LadrilloSolido ladrillo) {
    	entidades_ladrillo_solido.add(ladrillo);
    }
    public void agregar_tuberia(Tuberias tuberia) {
    	entidades_tuberias.add(tuberia);
    }
    public void agregar_vacio(Vacio vacio) {
    	entidades_vacio.add(vacio);
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
    public void eliminar_bloque_solido(BloqueSolido solido) {
    	entidades_bloque_solido.remove(solido);
    }
    public void eliminar_bloque_de_pregunta(BloqueDePregunta pregunta) {
    	entidades_bloque_de_pregunta.remove(pregunta);
    }
    public void eliminar_ladrillo_solido(LadrilloSolido ladrillo) {
    	entidades_ladrillo_solido.remove(ladrillo);
    }
    public void eliminar_tuberia(Tuberias tuberia) {
    	entidades_tuberias.remove(tuberia);
    }
    public void eliminar_vacio(Vacio vacio) {
    	entidades_vacio.remove(vacio);
    }
    
    public void eliminar_bola_de_fuego(BolaDeFuego bola_de_fuego) {
    	entidades_proyectiles.remove(bola_de_fuego);
    }
    
    public void resetear_mapa() {
        for (Enemigo enemigo : entidades_enemigo) enemigo.destruir();
        	entidades_enemigo.clear();
        
        for (BloqueSolido bloque : entidades_bloque_solido) bloque.destruir();
        	entidades_bloque_solido.clear();
        
        for (BloqueDePregunta pregunta : entidades_bloque_de_pregunta) pregunta.destruir();
        	entidades_bloque_de_pregunta.clear();
        	
        for(LadrilloSolido ladrillo : entidades_ladrillo_solido) ladrillo.destruir();
        	entidades_ladrillo_solido.clear();
        
        for (Tuberias tuberia : entidades_tuberias) tuberia.destruir();
        	entidades_tuberias.clear();
        	
        for (Vacio vacio : entidades_vacio) vacio.destruir();
        	entidades_vacio.clear();
        	
        for (PowerUp powerup : entidades_powerup) powerup.destruir();
        	entidades_powerup.clear();
        
        for (BolaDeFuego bolaDeFuego : entidades_proyectiles) bolaDeFuego.destruir();
        	entidades_proyectiles.clear();
        
        if (mario != null) mario.destruir();

    }

    public boolean nivel_completado() {

    	return false;
    }
    
    public void reproducir_efecto(String efecto) {
    	mi_juego.reproducir_efecto(efecto);
    }
    
}