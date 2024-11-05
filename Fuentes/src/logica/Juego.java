package logica;

import java.awt.EventQueue;

import entidades.mario.Mario;
import fabricas.EntidadesFactory;
import fabricas.SpritesFactory;
import gui.ControladorDeVistas;
import niveles.Nivel;

/**
 * La clase {@code Juego} gestiona la lógica central del juego, incluyendo la inicialización de 
 * entidades, controladores y el estado del juego. Implementa el patrón Singleton 
 * para asegurar que solo exista una instancia de juego en ejecución.
 */
public class Juego {
    private static Juego instancia_juego;
    
    protected SpritesFactory fabrica_sprites;
    protected EntidadesFactory fabrica_entidades;
    protected ControladorDeVistas controlador_vistas;
    protected Nivel nivel_actual;
    protected Mapa mapa_nivel_actual;
    protected String nombre_jugador;

    protected ControladorMovimiento controlador_movimiento;
    protected ControladorEntidades controlador_entidades;
    protected ControladorNivel controlador_nivel;
    protected ControladorRanking controlador_ranking;

    private Juego() {
        iniciar();
    }
    
    public static Juego get_instancia() {
        if (instancia_juego == null) {
            instancia_juego = new Juego();
        }
        return instancia_juego;
    }

    private void iniciar() {
    	
        mapa_nivel_actual = new Mapa(this);
        
        // Inicializa controladores
        controlador_ranking = new ControladorRanking(this);
        controlador_vistas = new ControladorDeVistas(this);
        controlador_movimiento = new ControladorMovimiento(this);
        controlador_entidades = new ControladorEntidades(this);
        controlador_nivel = new ControladorNivel(this);
        
    }

    public void iniciar_hilos_movimiento() {
        controlador_movimiento.iniciar_hilos();
    }

    public void detener_hilos() {
        controlador_movimiento.detener_hilos();
    }
    
	public void cargar_datos(EntidadesFactory generador) {
		controlador_nivel.cargar_datos(generador);
	}
	
    public void cambiar_dominio(SpritesFactory nueva_fabrica) {
        this.fabrica_sprites = nueva_fabrica;
        if (mapa_nivel_actual != null) {
            mapa_nivel_actual.actualizar_fabrica_sprites(nueva_fabrica);
        }
    }

    // Getters
    public Nivel get_nivel_actual() { return nivel_actual; }
    public Mapa get_mapa_nivel_actual() { return mapa_nivel_actual; }
    public SpritesFactory get_fabrica_sprites() { return fabrica_sprites; }
    public void reproducir_efecto(String efecto) { controlador_vistas.reproducir_efecto(efecto); }
	public ControladorDeVistas get_controlador_vistas() { return controlador_vistas; }
	public ControladorNivel get_controlador_nivel() { return controlador_nivel; }
	public ControladorRanking get_controlador_ranking() { return controlador_ranking; }
	public ControladorMovimiento get_controlador_movimiento() { return controlador_movimiento; }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Juego.get_instancia();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void reiniciar_juego() {
        if (controlador_movimiento != null) {
            controlador_movimiento.terminar_juego();
        }
        
        Mario.get_instancia().reiniciar();
        
        controlador_movimiento = new ControladorMovimiento(this);
        controlador_entidades = new ControladorEntidades(this);
        controlador_nivel = new ControladorNivel(this);
        
        mapa_nivel_actual = new Mapa(this);
        nivel_actual = null;
    }
    
    public void pausar_juego() {
        if (controlador_movimiento != null) {
            controlador_movimiento.pausar();
        }
    }

    public void reanudar_juego() {
        if (controlador_movimiento != null) {
            controlador_movimiento.reanudar();
        }
    }
}