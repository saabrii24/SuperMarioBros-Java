package logica;

import java.awt.EventQueue;

import entidades.mario.Mario;
import fabricas.EntidadesFactory;
import fabricas.SpritesFactory;
import gui.ControladorDeVistas;
import niveles.Nivel;

public class Juego {
    private static Juego instancia_juego;
    public static final int SALTAR = 15000;
    public static final int IZQUIERDA = 15001;
    public static final int DERECHA = 15003;
    public static final int DISPARAR = 15004;

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
        
        // Inicializar controladores
        controlador_ranking = new ControladorRanking();
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
        Mario.get_instancia().set_fabrica_sprites(nueva_fabrica);
        // Actualizar todas las entidades existentes con la nueva fábrica
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Juego.get_instancia();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

