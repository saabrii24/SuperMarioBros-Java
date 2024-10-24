package logica;

import entidades.*;
import entidades.enemigos.*;
import entidades.mario.*;
import fabricas.*;
import gui.ControladorDeVistas;
import niveles.*;
import ranking.Ranking;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Juego {
	private static Juego instancia_juego;

    public static final int SALTAR = 15000;
    public static final int IZQUIERDA = 15001;
    public static final int DERECHA = 15003;
    public static final int DISPARAR = 15004;

    protected Thread hilo_mario_movimiento;
    protected Thread hilo_enemigos_movimiento;
    
    protected SpritesFactory fabrica_sprites;
    protected EntidadesFactory fabrica_entidades;
    protected ControladorDeVistas controlador_vistas;
    protected Nivel nivel_actual;
    protected Mapa mapa_nivel_actual;
    protected Ranking ranking;
    protected String nombre_jugador;

    protected boolean reiniciando_nivel = false;
    protected int tiempo_restante;
    protected volatile boolean esta_ejecutando;
    protected volatile int direccion_mario;
    protected boolean observer_registrado = false;
    protected int nivel_a_cargar;

    private Juego() {
        iniciar();
    }
    
    public static Juego get_instancia() {
        if (instancia_juego == null) {
        	instancia_juego = new Juego();
        }
        return instancia_juego ;
    }

    private void iniciar() {
        mapa_nivel_actual = new Mapa(this);
        fabrica_sprites = new Dominio1Factory();
        Mario.get_instancia().set_fabrica_sprites(fabrica_sprites);
        fabrica_entidades = new EntidadesFactory(fabrica_sprites);
        controlador_vistas = new ControladorDeVistas(this);
        nivel_a_cargar = 1;
        ranking = new Ranking();
        nombre_jugador = JOptionPane.showInputDialog(null, "Nombre del jugador: ", "Registrar jugador", JOptionPane.PLAIN_MESSAGE);

    }
    
    public Nivel get_nivel_actual() { return nivel_actual; }
    public Mapa get_mapa_nivel_actual() { return mapa_nivel_actual; }
    public void reproducir_efecto(String efecto) { controlador_vistas.reproducir_efecto(efecto); }

    public synchronized void iniciar_hilos_movimiento() {
        if (esta_ejecutando) {
            return;
        }
        esta_ejecutando = true;

        iniciar_hilo_movimiento_mario();
        iniciar_hilo_movimiento_enemigos();
    }

    private void iniciar_hilo_movimiento_mario() {
        hilo_mario_movimiento = new Thread(this::bucle_movimiento_jugador);
        hilo_mario_movimiento.start();
    }

    private void iniciar_hilo_movimiento_enemigos() {
        hilo_enemigos_movimiento = new Thread(this::bucle_movimiento_entidades);
        hilo_enemigos_movimiento.start();
    }

    private void bucle_movimiento_jugador() {
        final double tiempo_por_frame = 1_000_000_000.0 / 60; // 60 FPS

        while (esta_ejecutando) {
            long tiempo_actual = System.nanoTime();

            mover_mario();
            
            notificar_observadores_mario();
            controlador_vistas.get_pantalla_mapa().repaint();

            controlar_fps(tiempo_actual, tiempo_por_frame);
        }
    }

    private void bucle_movimiento_entidades() {
        final double tiempo_por_frame = 1_000_000_000.0 / 60; // 60 FPS

        while (esta_ejecutando) {
            long tiempo_actual = System.nanoTime();

            mover_enemigos();
            mover_proyectiles();
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_goomba());
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_buzzy_beetle());
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_koopa_troopa());
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_lakitu());
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_piranha_plant());
            notificar_observadores_entidades(mapa_nivel_actual.get_entidades_spiny());
            controlador_vistas.get_pantalla_mapa().repaint();

            controlar_fps(tiempo_actual, tiempo_por_frame);
        }
    }

    private void controlar_fps(long tiempo_actual, double tiempo_por_frame) {
        long tiempo_restante = (long) tiempo_por_frame - (System.nanoTime() - tiempo_actual);
        if (tiempo_restante > 0) {
            try {
                Thread.sleep(tiempo_restante / 1_000_000, (int) (tiempo_restante % 1_000_000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void mover_mario() {
        Mario mario = Mario.get_instancia();
        synchronized(mario) {
            if(mario.get_dimension() != null) { 
            	
            	mario.actualizar();
            	mapa_nivel_actual.actualizar_entidades();

                mario.finalizar_invulnerabilidad();
                if (mapa_nivel_actual.get_colisionador().get_murio_mario() && !reiniciando_nivel) reiniciar_nivel();            
            }
            
            mario.mover();

            //cambiar nivel al llegar al castillo
            if(mario.get_posicion_en_x() >= 4410) {
            	nivel_a_cargar++;
            	if(nivel_a_cargar > 3) {
            		controlador_vistas.accionar_pantalla_victoria();
            		actualizar_ranking();
            		detener_hilos();
            	} else {
            		mapa_nivel_actual.resetear_mapa();
            		cargar_datos(fabrica_entidades);
            		mario.set_estado(new NormalMarioState(mario));
            	}
            }
            if(mario.get_posicion_en_y() < 0) {
            	mapa_nivel_actual.get_colisionador().set_murio_mario(true);
            	mario.caer_en_vacio();
            	mario.destruir();
            }
            if(mario.get_vidas()==0) {
            	controlador_vistas.accionar_pantalla_derrota();
            	detener_hilos();
            }
        }
    }

    private void mover_enemigos() {
        List<Goomba> goombas = new ArrayList<>(mapa_nivel_actual.get_entidades_goomba());
        List<BuzzyBeetle> buzzys = new ArrayList<>(mapa_nivel_actual.get_entidades_buzzy_beetle());
        List<KoopaTroopa> koopas = new ArrayList<>(mapa_nivel_actual.get_entidades_koopa_troopa());
        List<Lakitu> lakitus = new ArrayList<>(mapa_nivel_actual.get_entidades_lakitu());
        List<Spiny> spinys = new ArrayList<>(mapa_nivel_actual.get_entidades_spiny());
        
        for (Goomba goomba : goombas) {
            synchronized(goomba) {
                try {                 
                    goomba.mover();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
        for (BuzzyBeetle buzzy : buzzys) {
            synchronized(buzzy) {
                try {                 
                    buzzy.mover();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
        for (KoopaTroopa koopa : koopas) {
            synchronized(koopa) {
                try {
                		koopa.mover();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
        for (Lakitu lakitu : lakitus) {
            synchronized(lakitu) {
                try {                 
                    lakitu.mover();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
        for (Spiny spiny : spinys) {
            synchronized(spiny) {
                try {                 
                    spiny.mover();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
    }
    
    private void mover_proyectiles() {
        List<BolaDeFuego> proyectiles = new ArrayList<>(mapa_nivel_actual.get_entidades_proyectiles());
        for (BolaDeFuego proyectil : proyectiles) {
            synchronized(proyectil) {
                try {                  
                    //controlador_colisiones.verificar_colision_proyectil(proyectil);
                    proyectil.mover();
                    //proyectil.actualizar();
                } catch (Exception e) {
                    // Si el proyectil fue eliminado mientras iterábamos
                    continue;
                }
            }
        }
    }

    public void notificar_observadores() {
        notificar_observadores_mario();
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_buzzy_beetle());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_koopa_troopa());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_goomba());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_lakitu());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_piranha_plant());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_spiny());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_powerup());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_proyectiles());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_bloque_de_pregunta());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_bloque_solido());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_ladrillo_solido());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_tuberias());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_vacio());
    }

    private void notificar_observadores_mario() {
        Mario mario = Mario.get_instancia();
        mario.notificar_observer();
    }

    private void notificar_observadores_entidades(List<? extends Entidad> entidades) {
        // Crear una copia de la lista para evitar ConcurrentModificationException
        List<? extends Entidad> entidadesCopia = new ArrayList<>(entidades);
        for (Entidad entidad : entidadesCopia) {
            synchronized(entidad) {
                try {
                    entidad.notificar_observer();
                } catch (Exception e) {
                    // Si la entidad fue eliminada mientras iterábamos
                    continue;
                }
            }
        }
    }

    public void cargar_datos(EntidadesFactory generador) {
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
            getClass().getResourceAsStream("/niveles/nivel-"+nivel_a_cargar+".txt"),
            generador,
            mapa_nivel_actual
        );
        fabrica_entidades = generador;   
        registrar_observers(); 
        iniciar_hilos_movimiento();
    }

    public void reiniciar_nivel() {
    	reiniciando_nivel = true;
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
                getClass().getResourceAsStream("/niveles/nivel-"+nivel_a_cargar+".txt"), fabrica_entidades, mapa_nivel_actual);

        mapa_nivel_actual.resetear_mapa();
        cargar_datos(fabrica_entidades);
        
        // Restablecer a Mario a su posición inicial
        Mario mario = Mario.get_instancia();
        mario.resetear_posicion();
        mapa_nivel_actual.agregar_mario(mario);
        
        tiempo_restante = nivel_actual.get_tiempo_restante();
        mapa_nivel_actual.get_colisionador().set_murio_mario(false);
        controlador_vistas.refrescar();
        reiniciando_nivel = false;
    }

    public void cargar_proximo_nivel() {
        nivel_a_cargar++;
        
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
        		getClass().getResourceAsStream("/niveles/nivel-"+nivel_a_cargar+".txt"), fabrica_entidades, mapa_nivel_actual);
        
        mapa_nivel_actual.resetear_mapa();
        cargar_datos(fabrica_entidades);
        
        Mario mario = Mario.get_instancia();
        mario.resetear_posicion();
        mapa_nivel_actual.agregar_mario(mario);
        
        tiempo_restante = nivel_actual.get_tiempo_restante();
        
        controlador_vistas.refrescar();
    }

    private void registrar_observers() {
        registrar_observer_jugador(Mario.get_instancia());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_bloque_de_pregunta());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_bloque_solido());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_ladrillo_solido());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_tuberias());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_vacio());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_buzzy_beetle());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_goomba());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_koopa_troopa());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_lakitu());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_piranha_plant());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_spiny());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_proyectiles());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_powerup());

    }

    protected void registrar_observer_jugador(Mario jugador_mario) {
        Observer observer_jugador = controlador_vistas.registrar_entidad(jugador_mario);
        jugador_mario.registrar_observer(observer_jugador);
        observer_registrado = true;
    }

    protected void registrar_observers_para_entidades(List<? extends Entidad> entidades) {
        // Crea una copia de la lista para evitar ConcurrentModificationException
        List<? extends Entidad> entidades_copia = new ArrayList<>(entidades);
        for (Entidad entidad : entidades_copia) {
            synchronized(entidad) {
                Observer observer = controlador_vistas.registrar_entidad(entidad);
                entidad.registrar_observer(observer);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
              try {
                Juego.get_instancia();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          });
    }
    
    public void actualizar_ranking() {
    	int contador_puntos = Mario.get_instancia().get_puntaje();
    	
        //Si no hay un input de nombre, se registra al jugador como invitado
        if (nombre_jugador != null && !nombre_jugador.trim().isEmpty()) {
        ranking.agregar_jugador(nombre_jugador, contador_puntos);
        }
        else {
        	ranking.agregar_jugador("Invitado", contador_puntos);
        }
     }
    
    public void detener_hilos() {
        esta_ejecutando = false;
        
        try {
            if (hilo_mario_movimiento != null) {
                hilo_mario_movimiento.join();
            }
            if (hilo_enemigos_movimiento != null) {
                hilo_enemigos_movimiento.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }    
    
    public SpritesFactory get_fabrica_sprites() {
    	return fabrica_sprites;
    }
    
}