package entidades.mario;

import entidades.BolaDeFuego;
import entidades.Entidad;
import entidades.EntidadMovible;
import entidades.enemigos.Enemigo;
import entidades.interfaces.EntidadJugador;
import entidades.interfaces.SistemaPuntuacion;
import entidades.interfaces.SistemaVidas;
import entidades.powerups.ChampiVerde;
import entidades.powerups.Estrella;
import entidades.powerups.FlorDeFuego;
import entidades.powerups.Moneda;
import entidades.powerups.ListaPowerUps;
import entidades.powerups.SuperChampi;
import fabricas.Sprite;
import fabricas.SpritesFactory;

public class Mario extends EntidadMovible implements EntidadJugador {
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

    // Atributos de movimiento
    private double velocidad_en_x;
    private double velocidad_en_y;
    
    // Constructor privado del Singleton
    private Mario(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        this.sprites_factory = null;
        this.sistema_puntuacion = new PuntuacionMario();
        this.sistema_vidas = new VidasMario(3);

        this.movimiento_derecha = true;
       // this.estado = new NormalMarioState(this);
    }

    // Singleton: Obtiene la instancia de Mario
    public static Mario get_instancia() {
        if (instancia_mario == null) {
        	instancia_mario = new Mario(150, 150, null);
        }
        return instancia_mario;
    }

    // Interfaz del estado de Mario
    public interface MarioState {
        boolean matar_si_hay_colision(Enemigo enemigo);
        void consumir_estrella();
    	void consumir_super_champi();
    	void consumir_flor_de_fuego();
		void actualizar_sprite();
    }

    // Getters
    public SistemaPuntuacion get_sistema_puntuacion() {
        return sistema_puntuacion;
    }

    public SistemaVidas get_sistema_vidas() {
        return sistema_vidas;
    }

    public int get_direccion() {
        return direccion;
    }

    public MarioState get_estado_anterior() {
        return estado_anterior;
    }

    public int get_direccion_mario() {
        return movimiento_derecha ? 1 : -1;
    }
    
	public boolean get_movimiento_derecha() {
		return movimiento_derecha;
	}
    
	public SpritesFactory get_sprite_factory() {
		return sprites_factory;
	}

    // Setters
    public void set_direccion_mario(int direccion_mario) {
        direccion = direccion_mario;
    }

    public void set_fabrica_sprites(SpritesFactory sprites) {
        this.sprites_factory = sprites;
    }

    public void set_velocidad_en_x_mario(int vel) {
        velocidad_en_x = vel;
    }

    // Métodos de estado y movimiento
    public void cambiar_estado(MarioState nuevo_estado) {
        this.estado_anterior = this.estado;
        this.estado = nuevo_estado;
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
        actualizar_posicion();
    }

    private void mover_a_izquierda() {
        velocidad_en_x = -5;
        movimiento_derecha = false;
        actualizar_posicion();
    }

    private void detener_movimiento_horizontal() {
        velocidad_en_x = 0;
        actualizar_posicion();
    }

    private void detener_movimiento_vertical() {
    	velocidad_en_y = 0;
    	actualizar_posicion();
    }
    
    public void saltar() {
        	System.out.println("Saltando");
        	if (!saltando && contador_saltos < 1) {
        		contador_saltos+= 1;
        		saltando = true;
        		velocidad_en_y = -5; // Velocidad inicial del salto
        		actualizar_posicion();}
        
    }
    
    public void set_contador_saltos(int c) {
    	contador_saltos = c;
    }
    public int get_contador_saltos() {
    	return contador_saltos;
    }
    public void actualizar_posicion() {
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
        //estado.actualizar_sprite(); <- sería para cambiarla segun el estado, pero no anda
        actualizar_sprite();
    }

    private void actualizar_sprite() {
        if (saltando || velocidad_en_y < 0) {  // Saltando o cayendo (velocidad negativa)
            cambiar_sprite(movimiento_derecha ? 
                sprites_factory.get_mario_saltando_derecha() : 
                sprites_factory.get_mario_saltando_izquierda());
        } else if (velocidad_en_x != 0 && !saltando) {
            cambiar_sprite(movimiento_derecha ? 
                sprites_factory.get_mario_movimiento_derecha() : 
                sprites_factory.get_mario_movimiento_izquierda());
        } else {
            cambiar_sprite(movimiento_derecha ? 
                sprites_factory.get_mario_ocioso_derecha() : 
                sprites_factory.get_mario_ocioso_izquierda());
        }
    }

    protected void cambiar_sprite(Sprite nuevo_sprite) {
        this.sprite = nuevo_sprite;
    }

 // Métodos de interacción y puntaje
    public void consumir(ListaPowerUps power_up) {
        switch(power_up) {
        	case MONEDA : consumir_moneda();
        	case FLOR_DE_FUEGO: get_instancia().estado.consumir_flor_de_fuego();
        	case SUPER_CHAMPI: get_instancia().estado.consumir_super_champi();
        	case CHAMPI_VERDE: consumir_champi_verde();
        	case ESTRELLA: get_instancia().estado.consumir_estrella();
        }
    }
    
    private void consumir_moneda() {
    	get_sistema_puntuacion().sumar_puntos(5);
    	get_sistema_puntuacion().sumar_moneda();
	}

	private void consumir_champi_verde() {
		get_sistema_puntuacion().sumar_puntos(100);
		get_sistema_vidas().sumar_vida();
	}

    public void matar_si_hay_colision(Enemigo enemigo) {
        if (estado.matar_si_hay_colision(enemigo)) {
        	get_sistema_puntuacion().sumar_puntos(calcular_puntaje_enemigo(enemigo));
        } else {
        	get_sistema_puntuacion().sumar_puntos(calcular_penalizacion_enemigo(enemigo));
            get_sistema_vidas().quitar_vida();
        }
    }

    private int calcular_puntaje_enemigo(Enemigo enemigo) {
        return switch (enemigo.getClass().getSimpleName()) {
            case "Goomba" -> 60;
            case "KoopaTroopa" -> 90;
            case "PiranhaPlant", "BuzzyBeetle" -> 30;
            case "Lakitu", "Spiny" -> 60;
            default -> 0;
        };
    }

    private int calcular_penalizacion_enemigo(Enemigo enemigo) {
        return switch (enemigo.getClass().getSimpleName()) {
            case "Goomba", "PiranhaPlant", "Spiny" -> -30;
            case "KoopaTroopa" -> -45;
            case "BuzzyBeetle" -> -15;
            default -> 0;
        };
    }

    public void caer_en_vacio() {
    	get_sistema_puntuacion().restar_puntos(15);
    	get_sistema_vidas().quitar_vida();
    }

    public BolaDeFuego disparar() {
        return new BolaDeFuego(get_posicion_en_x(), get_posicion_en_y(), sprites_factory.get_bola_de_fuego());
    }

	@Override
	public int get_puntaje() {
		return get_sistema_puntuacion().get_puntaje_total();
	}

	@Override
	public int get_monedas() {
		return get_sistema_puntuacion().get_monedas();
	}

	@Override
	public int get_vidas() {
		return get_sistema_vidas().get_vidas();
	}

}