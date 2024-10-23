package entidades.mario;

import entidades.BolaDeFuego;
import entidades.EntidadMovible;
import entidades.enemigos.Enemigo;
import entidades.interfaces.EntidadJugador;
import entidades.interfaces.SistemaPuntuacion;
import entidades.interfaces.SistemaVidas;
import entidades.powerups.ChampiVerde;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.Moneda;
import entidades.powerups.PowerUpVisitor;
import entidades.powerups.SuperChampi;
import fabricas.Sprite;
import fabricas.SpritesFactory;
import logica.Mapa;

public class Mario extends EntidadMovible implements EntidadJugador,PowerUpVisitor {
	private static final long serialVersionUID = 1L;

	// Atributos estáticos y del singleton
    private static Mario instancia_mario;

    // Atributos de estado y puntaje
    private MarioState estado;
    private MarioState estado_anterior;
    private final SistemaPuntuacion sistema_puntuacion;
    private final SistemaVidas sistema_vidas;
    private SpritesFactory sprites_factory;
    
    private int direccion;
    private int contador_saltos = 0;
    private boolean movimiento_derecha;
    
    private boolean movimiento_horizontal_bloqueado;
    private boolean movimiento_vertical_bloqueado;
    
    // Constructor privado del Singleton
    private Mario(double x, double y, Sprite sprite) {
        super(x, y, sprite);
        this.sprites_factory = null;
        this.sistema_puntuacion = new ControladorPuntuacionMario();
        this.sistema_vidas = new ControladorVidasMario(3);
        this.movimiento_derecha = true;
        set_estado(new NormalMarioState(this));
    }

    // Singleton: Obtiene la instancia de Mario
    public static Mario get_instancia() {
        if (instancia_mario == null) {
        	instancia_mario = new Mario(96, 150, null);
        }
        return instancia_mario;
    }

    // Interfaz del estado de Mario
    public interface MarioState {
        boolean mata_tocando();
        boolean rompe_bloque();
        boolean colision_con_enemigo(Enemigo enemigo);
        BolaDeFuego disparar();
        void consumir_estrella();
    	void consumir_super_champi();
    	void consumir_flor_de_fuego();
    	void finalizar_invulnerabilidad();
		void actualizar_sprite();
    }

    // Getters
   
    public SistemaPuntuacion get_sistema_puntuacion() { return sistema_puntuacion; }
    public SistemaVidas get_sistema_vidas() { return sistema_vidas; }
    public int get_direccion() { return direccion; }
    public MarioState get_estado_anterior() { return estado_anterior; }
    public int get_direccion_mario() { return movimiento_derecha ? 1 : -1; } 
	public boolean get_movimiento_derecha() { return movimiento_derecha; }
	public boolean esta_saltando() { return saltando; }	
	public SpritesFactory get_sprite_factory() { return sprites_factory; }
    public int get_contador_saltos() { return contador_saltos;  }
	public int get_puntaje() { return get_sistema_puntuacion().get_puntaje_total(); }
	public int get_monedas() { return get_sistema_puntuacion().get_monedas(); }
	public int get_vidas() { return get_sistema_vidas().get_vidas(); }
    
    // Setters
    public void set_direccion_mario(int direccion_mario) { direccion = direccion_mario; }
    public void set_fabrica_sprites(SpritesFactory sprites) { this.sprites_factory = sprites; }
    public void set_velocidad_en_x_mario(double vel) { velocidad_en_x = vel; }
    public void set_contador_saltos(int c) { contador_saltos = c; }
    public void set_estado(MarioState state) { estado = state; }
    public void set_estado_anterior (MarioState state) { estado_anterior = state; }

    // Métodos de estado y movimiento
    public void cambiar_estado(MarioState nuevo_estado) {
        set_estado_anterior(this.estado);
        set_estado(nuevo_estado);
    }

    public void bloquear_movimiento_horizontal() {
        movimiento_horizontal_bloqueado = true;
        detener_movimiento_horizontal();
    }
    public void bloquear_movimiento_vertical() {
    	movimiento_vertical_bloqueado = true;
    	detener_movimiento_vertical();
    }
    public void activar_movimiento_horizontal() {
        movimiento_horizontal_bloqueado = false;
    }
    public void activar_movimiento_vertical() {
    	movimiento_vertical_bloqueado = false;
    }

    // Movimiento y lógica del juego
    @Override
    public void mover() {
        if(!movimiento_vertical_bloqueado) {
            switch (direccion) {
                case 1 -> mover_a_derecha();
                case -1 -> mover_a_izquierda();
                default -> detener_movimiento_horizontal();
            }
        }
    }

    private void mover_a_derecha() {
        velocidad_en_x = 5;
        movimiento_derecha = true;
    }

    private void mover_a_izquierda() {
        velocidad_en_x = -5;
        movimiento_derecha = false;
    }

    private void detener_movimiento_horizontal() {
        velocidad_en_x = 0;
    }

    private void detener_movimiento_vertical() {
    	velocidad_en_y = 0;
    }
    
    public void saltar() {
        if (!saltando && contador_saltos < 1) {
        	saltando = true;
        	contador_saltos++;
        	velocidad_en_y = -8; // Velocidad inicial del salto
        }
    }
    
    public void actualizar() {
        if (cayendo) {
        	velocidad_en_y += EntidadMovible.GRAVEDAD;
            posicion_en_y -= velocidad_en_y;
        }
        if (!cayendo) {
            detener_movimiento_horizontal();
        }

        if (saltando) {
            velocidad_en_y -= EntidadMovible.GRAVEDAD; // Disminuir la velocidad para simular el salto
            posicion_en_y -= velocidad_en_y;

            if (velocidad_en_y <= 0) {
                saltando = false; // Deja de saltar
                cayendo = true;   // Comienza a caer
            }
        }

        posicion_en_x += velocidad_en_x;
        estado.actualizar_sprite();
    }

	protected void cambiar_sprite(Sprite nuevo_sprite) { this.sprite = nuevo_sprite; }

 // Métodos de interacción y puntaje
    
    private void consumir_moneda() {
    	get_sistema_puntuacion().sumar_puntos(5);
    	get_sistema_puntuacion().sumar_moneda();
	}

	private void consumir_champi_verde() {
		get_sistema_puntuacion().sumar_puntos(100);
		get_sistema_vidas().sumar_vida();
	}

	// ----- IMPLEMENTAR CON VISITOR -----
    int calcular_puntaje_enemigo(Enemigo enemigo) {
        return switch (enemigo.getClass().getSimpleName()) {
            case "Goomba" -> 60;
            case "KoopaTroopa" -> 90;
            case "PiranhaPlant", "BuzzyBeetle" -> 30;
            case "Lakitu", "Spiny" -> 60;
            default -> 0;
        };
    }

    int calcular_penalizacion_enemigo(Enemigo enemigo) {
        return switch (enemigo.getClass().getSimpleName()) {
            case "Goomba", "PiranhaPlant", "Spiny" -> -30;
            case "KoopaTroopa" -> -45;
            case "BuzzyBeetle" -> -15;
            default -> 0;
        };
    }

    // ------------------------------------
    
    public void caer_en_vacio() {
    	get_sistema_puntuacion().restar_puntos(15);
    	get_sistema_vidas().quitar_vida();
    }

	public void visitar(Moneda moneda) { consumir_moneda(); }
	public void visitar(FlorDeFuego flor_de_fuego) { estado.consumir_flor_de_fuego(); }
	public void visitar(SuperChampi super_champi) { estado.consumir_super_champi(); }
	public void visitar(ChampiVerde champi_verde) { consumir_champi_verde(); }
	public void visitar(Estrella estrella) { estado.consumir_estrella(); }
	
    public BolaDeFuego disparar() { return estado.disparar(); }
    public void resetear_posicion() { this.set_posicion(96, 600); }
	public void finalizar_invulnerabilidad() { estado.finalizar_invulnerabilidad(); }	
	public  boolean mata_tocando() { return estado.mata_tocando(); }	
	public boolean rompe_bloque() { return estado.rompe_bloque(); }
	public boolean colision_con_enemigo(Enemigo e) { return estado.colision_con_enemigo(e); }
}