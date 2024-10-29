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
import logica.Juego;

public class Mario extends EntidadMovible implements EntidadJugador,PowerUpVisitor {
    private static final double VELOCIDAD_LATERAL = 5.0;
    private static final double VELOCIDAD_SALTO = -10.0;
    private static final int VIDAS_INICIALES = 3;
    private static final double POSICION_INICIAL_X = 96;
    private static final double POSICION_INICIAL_Y = 432;

    // Instancia de Mario Singleton
    private static Mario instancia_mario;

    // Estado y sistemas
    private MarioState estado;
    private SistemaPuntuacion sistema_puntuacion;
    private SistemaVidas sistema_vidas;
    private SpritesFactory sprites_factory;
    
    // Control de movimiento
    private int direccion;
    private int contador_saltos;
    private boolean movimiento_derecha;
    private boolean movimiento_vertical_bloqueado;
    
    // Constructor privado del Singleton
    private Mario(double x, double y, Sprite sprite) {
        super(x, y, sprite);
        inicializar_componentes();
    }
    
    private void inicializar_componentes() {
        this.sprites_factory = null;
        this.sistema_puntuacion = new ControladorPuntuacionMario();
        this.sistema_vidas = new ControladorVidasMario(VIDAS_INICIALES);
        this.movimiento_derecha = true;
        this.contador_saltos = 0;
        set_estado(new NormalMarioState(this));
    }


    // Singleton: Obtiene la instancia de Mario
    public static Mario get_instancia() {
        if (instancia_mario == null) {
            instancia_mario = new Mario(POSICION_INICIAL_X, POSICION_INICIAL_Y, null);
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
    public int get_direccion_mario() { return movimiento_derecha ? 1 : -1; }
    public boolean get_movimiento_derecha() { return movimiento_derecha; }
    public boolean esta_saltando() { return saltando; }
    public SpritesFactory get_sprite_factory() { return sprites_factory; }
    public int get_contador_saltos() { return contador_saltos; }
    public int get_puntaje() { return sistema_puntuacion.get_puntaje_total(); }
    public int get_monedas() { return sistema_puntuacion.get_monedas(); }
    public int get_vidas() { return sistema_vidas.get_vidas(); }
    
    // Setters
    public void set_direccion_mario(int direccion_mario) { this.direccion = direccion_mario; }
    public void set_fabrica_sprites(SpritesFactory sprites) { this.sprites_factory = sprites; }
    public void set_velocidad_en_x_mario(double vel) { this.velocidad_en_x = vel; }
    public void set_contador_saltos(int c) { this.contador_saltos = c; }
    public void set_estado(MarioState state) { this.estado = state; }

    // Métodos de estado y movimiento
    public void cambiar_estado(MarioState nuevo_estado) {
        set_estado(nuevo_estado);
    }

    public void bloquear_movimiento_vertical() {
    	movimiento_vertical_bloqueado = true;
    	detener_movimiento_vertical();
    }

    public void activar_movimiento_vertical() {
    	movimiento_vertical_bloqueado = false;
    }

    // Movimiento y lógica del juego
    @Override
    public void mover() {
        if (!movimiento_vertical_bloqueado) {
            switch (direccion) {
                case 1 -> mover_a_derecha();
                case -1 -> mover_a_izquierda();
                default -> detener_movimiento_horizontal();
            }
        }
    }

    private void mover_a_derecha() {
        velocidad_en_x = VELOCIDAD_LATERAL;
        movimiento_derecha = true;
    }

    private void mover_a_izquierda() {
        // Permitir el movimiento a la izquierda si no está en el borde
        if (posicion_en_x > 0) {
            velocidad_en_x = -VELOCIDAD_LATERAL;
            movimiento_derecha = false;
        } else {
            posicion_en_x = 0;
            detener_movimiento_horizontal();
        }
    }

    public void detener_movimiento_horizontal() {
    	//System.out.println("X: " + posicion_en_x + " - Y: " + posicion_en_y); <- Para diagramar entidades en nivel
        velocidad_en_x = 0;
    }

    private void detener_movimiento_vertical() {
    	velocidad_en_y = 0;
    }
    
    public void saltar() {
        if (!saltando && contador_saltos < 1) {
            iniciar_salto();
        }
    }
    
    private void iniciar_salto() {
        Juego.get_instancia().reproducir_efecto("jump-small");
        saltando = true;
        contador_saltos++;
        velocidad_en_y = VELOCIDAD_SALTO;
    }

    
    public void actualizar() {
        actualizar_movimiento_vertical();
        actualizar_posicion();
        estado.actualizar_sprite();
    }

    private void actualizar_movimiento_vertical() {
        if (cayendo) {
            aplicar_gravedad();
        }
        if (saltando) {
            actualizar_salto();
        }
    }
    
    private void aplicar_gravedad() {
        velocidad_en_y += EntidadMovible.GRAVEDAD;
        posicion_en_y -= velocidad_en_y;
    }

    private void actualizar_salto() {
        velocidad_en_y -= EntidadMovible.GRAVEDAD;
        posicion_en_y -= velocidad_en_y;

        if (velocidad_en_y <= 0) {
            finalizar_salto();
        }
    }

    private void finalizar_salto() {
        saltando = false;
        cayendo = true;
    }

    private void actualizar_posicion() {
        if (!cayendo) {
            detener_movimiento_horizontal();
        }
        posicion_en_x += velocidad_en_x;
    }

	protected void cambiar_sprite(Sprite nuevo_sprite) { this.sprite = nuevo_sprite; }

 // Métodos de interacción y puntaje
    
    public void consumir_moneda() {
    	Juego.get_instancia().reproducir_efecto("coin");
    	get_sistema_puntuacion().sumar_puntos(5);
    	get_sistema_puntuacion().sumar_moneda();
	}

    public void consumir_champi_verde() {
    	Juego.get_instancia().reproducir_efecto("powerup");
		get_sistema_puntuacion().sumar_puntos(100);
		get_sistema_vidas().sumar_vida();
	}

    public void caer_en_vacio() {
    	cambiar_estado(new NormalMarioState(Mario.get_instancia()));
    	get_sistema_puntuacion().restar_puntos(15);
    	get_sistema_vidas().quitar_vida();
    	
    }

	public void visitar(Moneda moneda) { consumir_moneda(); }
	public void visitar(FlorDeFuego flor_de_fuego) { estado.consumir_flor_de_fuego(); Juego.get_instancia().reproducir_efecto("powerup");}
	public void visitar(SuperChampi super_champi) { estado.consumir_super_champi(); Juego.get_instancia().reproducir_efecto("powerup");}
	public void visitar(ChampiVerde champi_verde) { consumir_champi_verde(); }
	public void visitar(Estrella estrella) { estado.consumir_estrella(); Juego.get_instancia().reproducir_efecto("powerup");}
	
    public BolaDeFuego disparar() { return estado.disparar(); }
    public void resetear_posicion() { this.set_posicion(96, 432); }
	public void finalizar_invulnerabilidad() { estado.finalizar_invulnerabilidad(); }	
	public  boolean mata_tocando() { return estado.mata_tocando(); }	
	public boolean rompe_bloque() { return estado.rompe_bloque(); }
	public boolean colision_con_enemigo(Enemigo enemigo) { return estado.colision_con_enemigo(enemigo); }
}