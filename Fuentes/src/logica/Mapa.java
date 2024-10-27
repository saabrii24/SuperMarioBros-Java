package logica;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import entidades.BolaDeFuego;
import entidades.EntidadMovible;
import entidades.enemigos.*;
import entidades.interfaces.EntidadLogica;
import entidades.mario.Mario;
import entidades.plataformas.*;
import entidades.powerups.PowerUp;
import fabricas.SpritesFactory;

public class Mapa {

    protected Juego mi_juego;
    protected Mario mario;
    
    protected List<Goomba> entidades_goomba;
    protected List<BuzzyBeetle> entidades_buzzy_beetle;
    protected List<KoopaTroopa> entidades_koopa_troopa;
    protected List<Lakitu> entidades_lakitu;
    protected List<PiranhaPlant> entidades_piranha_plant;
    protected List<Spiny> entidades_spiny;
    protected List<PowerUp> entidades_powerup;
    protected List<BloqueSolido> entidades_bloque_solido;
    protected List<BloqueDePregunta> entidades_bloque_de_pregunta;
    protected List<LadrilloSolido> entidades_ladrillo_solido;
    protected List<Tuberias> entidades_tuberias;
    protected List<Vacio> entidades_vacio;
    protected List<BolaDeFuego> entidades_proyectiles;
    protected Colisionador colisionador;

    public Mapa(Juego juego) {
        this.mi_juego = juego;
        this.entidades_goomba = new CopyOnWriteArrayList<>();
        this.entidades_buzzy_beetle = new CopyOnWriteArrayList<>();
        this.entidades_lakitu = new CopyOnWriteArrayList<>();
        this.entidades_koopa_troopa = new CopyOnWriteArrayList<>();
        this.entidades_piranha_plant = new CopyOnWriteArrayList<>();
        this.entidades_spiny = new CopyOnWriteArrayList<>();
        this.entidades_powerup = new CopyOnWriteArrayList<>();
        this.entidades_bloque_solido = new CopyOnWriteArrayList<>();
        this.entidades_bloque_de_pregunta = new CopyOnWriteArrayList<>();
        this.entidades_ladrillo_solido = new CopyOnWriteArrayList<>();
        this.entidades_tuberias = new CopyOnWriteArrayList<>();
        this.entidades_vacio = new CopyOnWriteArrayList<>();
        this.entidades_proyectiles = new CopyOnWriteArrayList<>();
        this.colisionador = new Colisionador(this);
    }

    public void actualizar_entidades_movibles() {
        for (EntidadMovible entidad : get_entidades_movibles()) {
            entidad.actualizar();
        }
        colisionador.verificar_colisiones(mi_juego);
    }

    private List<EntidadMovible> get_entidades_movibles() {
        List<EntidadMovible> entidades_movibles = new CopyOnWriteArrayList<>();
        entidades_movibles.addAll(entidades_goomba);
        entidades_movibles.addAll(entidades_buzzy_beetle);
        entidades_movibles.addAll(entidades_koopa_troopa);
        entidades_movibles.addAll(entidades_piranha_plant);
        entidades_movibles.addAll(entidades_spiny);
        entidades_movibles.addAll(entidades_proyectiles);
        return entidades_movibles;
    }

    public Colisionador get_colisionador() { return colisionador; }

    public List<Goomba> get_entidades_goomba() { return entidades_goomba; }
    public List<BuzzyBeetle> get_entidades_buzzy_beetle() { return entidades_buzzy_beetle; }
    public List<Lakitu> get_entidades_lakitu() { return entidades_lakitu; }
    public List<KoopaTroopa> get_entidades_koopa_troopa() { return entidades_koopa_troopa; }
    public List<PiranhaPlant> get_entidades_piranha_plant() { return entidades_piranha_plant; }
    public List<Spiny> get_entidades_spiny() { return entidades_spiny; }
    public List<PowerUp> get_entidades_powerup() { return entidades_powerup; }
    public List<BloqueSolido> get_entidades_bloque_solido() { return entidades_bloque_solido; }
    public List<BloqueDePregunta> get_entidades_bloque_de_pregunta() { return entidades_bloque_de_pregunta; }
    public List<LadrilloSolido> get_entidades_ladrillo_solido() { return entidades_ladrillo_solido; }
    public List<Tuberias> get_entidades_tuberias() { return entidades_tuberias; }
    public List<Vacio> get_entidades_vacio() { return entidades_vacio; }
    public List<BolaDeFuego> get_entidades_proyectiles() { return entidades_proyectiles; }

    public void agregar_mario(Mario mario) { this.mario = mario; }

    public void agregar_goomba(Goomba goomba) { entidades_goomba.add(goomba); }
    public void agregar_buzzy_beetle(BuzzyBeetle buzzy) { entidades_buzzy_beetle.add(buzzy); }
    public void agregar_koopa_troopa(KoopaTroopa koopa) { entidades_koopa_troopa.add(koopa); }
    public void agregar_lakitu(Lakitu laki) { entidades_lakitu.add(laki); }
    public void agregar_piranha_plant(PiranhaPlant piranha) { entidades_piranha_plant.add(piranha); }
    public void agregar_spiny(Spiny spiny) { entidades_spiny.add(spiny); }
    public void agregar_bloque_solido(BloqueSolido solido) { entidades_bloque_solido.add(solido); }
    public void agregar_bloque_de_pregunta(BloqueDePregunta pregunta) { entidades_bloque_de_pregunta.add(pregunta); }
    public void agregar_ladrillo_solido(LadrilloSolido ladrillo) { entidades_ladrillo_solido.add(ladrillo); }
    public void agregar_tuberia(Tuberias tuberia) { entidades_tuberias.add(tuberia); }
    public void agregar_vacio(Vacio vacio) { entidades_vacio.add(vacio); }
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

    public void eliminar_goomba(Goomba goomba) { entidades_goomba.remove(goomba); }
    public void eliminar_buzzy_beetle(BuzzyBeetle buzzy) { entidades_buzzy_beetle.remove(buzzy); }
    public void eliminar_koopa_troopa(KoopaTroopa koopa) { entidades_koopa_troopa.remove(koopa); }
    public void eliminar_lakitu(Lakitu lakitu) { entidades_lakitu.remove(lakitu); }
    public void eliminar_piranha_plant(PiranhaPlant piranha) { entidades_piranha_plant.remove(piranha); }
    public void eliminar_spiny(Spiny spiny) { entidades_spiny.remove(spiny); }
    public void eliminar_powerup(PowerUp powerup) { entidades_powerup.remove(powerup); }
    public void eliminar_bloque_solido(BloqueSolido solido) { entidades_bloque_solido.remove(solido); }
    public void eliminar_bloque_de_pregunta(BloqueDePregunta pregunta) { entidades_bloque_de_pregunta.remove(pregunta); }
    public void eliminar_ladrillo_solido(LadrilloSolido ladrillo) { entidades_ladrillo_solido.remove(ladrillo); }
    public void eliminar_tuberia(Tuberias tuberia) { entidades_tuberias.remove(tuberia); }
    public void eliminar_vacio(Vacio vacio) { entidades_vacio.remove(vacio); }
    public void eliminar_bola_de_fuego(BolaDeFuego bola_de_fuego) { entidades_proyectiles.remove(bola_de_fuego); }

    public void barrer_mapa() {
        for (EntidadMovible entidad : get_entidades_movibles()) {
            entidad.eliminar_del_mapa();
        }
        entidades_goomba.clear();
        entidades_buzzy_beetle.clear();
        entidades_koopa_troopa.clear();
        entidades_lakitu.clear();
        entidades_piranha_plant.clear();
        entidades_spiny.clear();
        for (PowerUp powerup : entidades_powerup) powerup.eliminar_del_mapa(); 
        entidades_powerup.clear();
        for (BloqueSolido bloque_solido : entidades_bloque_solido) bloque_solido.eliminar_del_mapa(); 
        entidades_bloque_solido.clear();
        for (BloqueDePregunta bloque_pregunta : entidades_bloque_de_pregunta) bloque_pregunta.eliminar_del_mapa(); 
        entidades_bloque_de_pregunta.clear();
        for (LadrilloSolido ladrillo_solido : entidades_ladrillo_solido) ladrillo_solido.eliminar_del_mapa(); 
        entidades_ladrillo_solido.clear();
        for (Tuberias tuberia : entidades_tuberias) tuberia.eliminar_del_mapa(); 
        entidades_tuberias.clear();
        for (Vacio vacio : entidades_vacio) vacio.eliminar_del_mapa(); 
        entidades_vacio.clear();
        for (BolaDeFuego proyectil : entidades_proyectiles) proyectil.eliminar_del_mapa(); 
        entidades_proyectiles.clear();
        
        if (mario != null) {
        	mario.eliminar_del_mapa();
        	mario = null;
        }
    }

    public void reproducir_efecto(String efecto) { mi_juego.reproducir_efecto(efecto); }

    public void actualizar_fabrica_sprites(SpritesFactory nueva_fabrica) {
        for (KoopaTroopa koopa : entidades_koopa_troopa)
        	koopa.actualizar_fabrica_sprites(nueva_fabrica);
        for (Spiny spiny : entidades_spiny)
        	spiny.actualizar_fabrica_sprites(nueva_fabrica);
        for (BuzzyBeetle buzzy : entidades_buzzy_beetle)
        	buzzy.actualizar_fabrica_sprites(nueva_fabrica);
    }

}