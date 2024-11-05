package logica;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;

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

    public void actualizar_entidades_movibles() {
        for (EntidadMovible entidad : get_entidades_movibles()) {
            entidad.actualizar();
        }
        colisionador.verificar_colisiones(mi_juego);
    }

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

    public void agregar_mario(Mario mario) { this.mario = mario; }

    public void agregar_goomba(Goomba goomba) { entidades_enemigos.add(goomba); }
    public void agregar_buzzy_beetle(BuzzyBeetle buzzy) { entidades_enemigos.add(buzzy); }
    public void agregar_koopa_troopa(KoopaTroopa koopa) { entidades_enemigos.add(koopa); }
    public void agregar_lakitu(Lakitu laki) { entidades_enemigos.add(laki); }
    public void agregar_piranha_plant(PiranhaPlant piranha) { entidades_enemigos.add(piranha); }
    public void agregar_spiny(Spiny spiny) { entidades_enemigos.add(spiny); }
    public void agregar_bloque_solido(BloqueSolido solido) { entidades_plataformas.add(solido); }
    public void agregar_bloque_de_pregunta(BloqueDePregunta pregunta) { entidades_plataformas.add(pregunta); }
    public void agregar_ladrillo_solido(LadrilloSolido ladrillo) { entidades_plataformas.add(ladrillo); }
    public void agregar_tuberia(Tuberias tuberia) { entidades_plataformas.add(tuberia); }
    public void agregar_vacio(Vacio vacio) { entidades_plataformas.add(vacio); }
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

    public void eliminar_goomba(Goomba goomba) { entidades_enemigos.remove(goomba); }
    public void eliminar_buzzy_beetle(BuzzyBeetle buzzy) { entidades_enemigos.remove(buzzy); }
    public void eliminar_koopa_troopa(KoopaTroopa koopa) { entidades_enemigos.remove(koopa); }
    public void eliminar_lakitu(Lakitu lakitu) { entidades_enemigos.remove(lakitu); }
    public void eliminar_piranha_plant(PiranhaPlant piranha) { entidades_enemigos.remove(piranha); }
    public void eliminar_spiny(Spiny spiny) { entidades_enemigos.remove(spiny); }
    public void eliminar_powerup(PowerUp powerup) { entidades_powerup.remove(powerup); }
    public void eliminar_bloque_solido(BloqueSolido solido) { entidades_plataformas.remove(solido); }
    public void eliminar_bloque_de_pregunta(BloqueDePregunta pregunta) { entidades_plataformas.remove(pregunta); }
    public void eliminar_ladrillo_solido(LadrilloSolido ladrillo) { entidades_plataformas.remove(ladrillo); }
    public void eliminar_tuberia(Tuberias tuberia) { entidades_plataformas.remove(tuberia); }
    public void eliminar_vacio(Vacio vacio) { entidades_plataformas.remove(vacio); }
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

    public void actualizar_fabrica_sprites(SpritesFactory nueva_fabrica) {
        for(Enemigo enemigo: entidades_enemigos) {
        	enemigo.actualizar_fabrica_sprites(nueva_fabrica);
        }
    }
    
    public void animacion_puntaje_obtenido(int x, int y, String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(mi_juego.controlador_vistas.get_pantalla_mapa().get_tipografia());
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        label.setSize(label.getPreferredSize());
        
        JScrollPane scroll_pane = mi_juego.controlador_vistas.get_pantalla_mapa().get_scroll_pane();
        Point posicion_en_scroll = scroll_pane.getViewport().getViewPosition();
        int screen_x = x - posicion_en_scroll.x;
        int screen_y = y - posicion_en_scroll.y + 350;
        label.setLocation(screen_x, screen_y);
        JLayeredPane layered_pane = mi_juego.controlador_vistas.get_pantalla_mapa().get_layered_pane();
        layered_pane.add(label, JLayeredPane.POPUP_LAYER);
        label.setBounds(screen_x, screen_y, label.getPreferredSize().width, label.getPreferredSize().height);
        
        Timer fade_timer = new Timer(50, null);
        final float[] alpha = {1.0f};
        
        fade_timer.addActionListener(e -> {
            alpha[0] -= 0.05f;
            if (alpha[0] <= 0) {
                fade_timer.stop();
                layered_pane.remove(label);
                layered_pane.repaint();
            } else {
                label.setForeground(new Color(255, 255, 255, (int)(alpha[0] * 255)));
                label.setLocation(label.getX(), label.getY() - 1);
            }
        });
        
        fade_timer.start();
    }

}