package logica;

import entidades.Entidad;
import entidades.enemigos.Enemigo;
import entidades.mario.Mario;
import fabricas.*;
import gui.ControladorDeVistas;
import niveles.GeneradorNivel;
import niveles.Nivel;
import java.util.List;import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

public class Juego extends Thread {

	public static final int SALTAR = 15000;
	public static final int IZQUIERDA = 15001;
	public static final int DERECHA = 15003;
	public static final int DISPARAR = 15004;
	
    protected ControladorDeVistas controlador_vistas;
    protected Nivel nivel_actual;
    protected SpritesFactory fabrica_sprites;
    protected EntidadesFactory fabrica_entidades;
    protected Mapa mapa_nivel_actual;
    protected int tiempo_restante;
    protected int contador_puntos;
    protected int vidas = 3;
    protected boolean esta_ejecutando;
    protected Thread hilo_juego;
    protected EntidadesFactory generador;
    protected volatile int direccion_mario;
    private volatile int direccion_enemigo = -1;
    protected boolean observer_registrado = false;
    protected Colisionador controlador_colisiones;

    public Juego() {
        iniciar();
    }

    private void iniciar() {
        mapa_nivel_actual = new Mapa(this);
        fabrica_sprites = new Dominio1Factory();
        Mario.get_instancia().set_fabrica_sprites(fabrica_sprites);
        fabrica_entidades = new EntidadesFactory(fabrica_sprites);
        controlador_vistas = new ControladorDeVistas(this);
        controlador_colisiones = new Colisionador(mapa_nivel_actual);
        if (!esta_ejecutando) {
            iniciar_thread_juego();
            iniciar_thread_movimiento();
            iniciar_thread_enemigos();
        }
    }
    
    private synchronized void iniciar_thread_juego() {
        if (esta_ejecutando)
            return;
        esta_ejecutando = true;
        hilo_juego = new Thread(this);
        hilo_juego.start();
    }

    public void set_direccion_mario(int direccion) {
        this.direccion_mario = direccion;
    }


    public void cargar_datos(EntidadesFactory generador) {
        nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
            getClass().getResourceAsStream("/niveles/nivel-1.txt"),
            generador,
            mapa_nivel_actual
        );
        fabrica_entidades = generador;
    }


   public void registrar_observers() {
        registrar_observer_jugador(Mario.get_instancia());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_enemigo());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_powerup());
        registrar_observers_para_entidades(mapa_nivel_actual.get_entidades_plataforma());
    }

    protected void registrar_observer_jugador(Mario jugador_mario) {
        Observer observer_jugador = controlador_vistas.registrar_entidad(jugador_mario);
        jugador_mario.registrar_observer(observer_jugador);
        observer_registrado = true;
    }

    protected void registrar_observers_para_entidades(List<? extends Entidad> entidades) {
        for (Entidad entidad : entidades) {
            Observer observer = controlador_vistas.registrar_entidad(entidad);
            entidad.registrar_observer(observer);
        }
    }
    
    public void set_generador(EntidadesFactory generador) {
    	this.generador = generador;
    }


	public Mapa get_mapa() {
    	return mapa_nivel_actual;
    }
    
    public Nivel get_nivel_actual() {
    	return nivel_actual;
    }
    
	/*public void mover_jugador(int d) {
		mapa_nivel_actual.mover_jugador(d);
	}*/

    public static void main(String[] args) {
        new Juego();
    }

    public void notificar_observadores() {
        notificar_observadores_mario();
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_enemigo());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_powerup());
        notificar_observadores_entidades(mapa_nivel_actual.get_entidades_plataforma());
    }

    private void notificar_observadores_mario() {
        Mario mario = Mario.get_instancia();
        if (mario != null) {
            mario.notificar_observer();
        }
    }

    private void notificar_observadores_entidades(List<? extends Entidad> entidades) {
        for (Entidad entidad : entidades) {
            entidad.notificar_observer();
        }
    }
    
    public void iniciar_thread_movimiento() {
        Thread hilo_movimiento = new Thread(() -> {
            while (true) {
                mover_jugador_continuo();
                try {
                    Thread.sleep(16); // Controlar la velocidad del movimiento
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hilo_movimiento.start();
    }
    
    private void mover_jugador_continuo() {
    	Mario.get_instancia().mover();
    	while (!observer_registrado) {
            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        notificar_observadores();
    }
    
    public void iniciar_thread_enemigos() {
    	Thread hilo_enemigos = new Thread(() -> mover_enemigos_continuo());
        hilo_enemigos.start();
    }
    
    private void mover_enemigos_continuo() {
    	while (!observer_registrado) {
    		 try {
                 Thread.sleep(16); 
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
             }
    	}
    	while (esta_ejecutando) {
    		for (Enemigo e : mapa_nivel_actual.get_entidades_enemigo()) {
    			controlador_colisiones.verificar_colision_enemigo(e);
    			e.mover();
    			e.notificar_observer();
    		}
    		notificar_observadores();
    		try {
    			Thread.sleep(16); // Velocidad del movimiento de los enemigos
    		} catch (InterruptedException e) {
    			Thread.currentThread().interrupt();
    		}
        }
    }


}