package entidades.enemigos;
import entidades.interfaces.EnemigosVisitor;
import entidades.interfaces.EnemigoVisitorEnemigo;
import entidades.plataformas.*;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

public class PiranhaPlant extends Enemigo {
    private static final double VELOCIDAD_VERTICAL = 0.4;
    private static final int DISTANCIA_MAXIMA = 96;
    private int posicion_inicial_y;
    private boolean moviendo_arriba = true;
    private double velocidad_actual = -VELOCIDAD_VERTICAL;

    public PiranhaPlant(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        this.posicion_inicial_y = y;
        this.cayendo = false;
    }

    //Movimiento
    private void cambiar_direccion() {
        if (moviendo_arriba) {
            if (get_posicion_en_y() <= posicion_inicial_y - DISTANCIA_MAXIMA) {
                moviendo_arriba = false;
                velocidad_actual = VELOCIDAD_VERTICAL;
            }
        } else {
            if (get_posicion_en_y() >= posicion_inicial_y) {
                moviendo_arriba = true;
                velocidad_actual = -VELOCIDAD_VERTICAL;
            }
        }
    }
    public void mover() {}

    //Actualizar
    public void actualizar() {
        this.cayendo = false;
        set_velocidad_en_y(0);
        cambiar_direccion();
        set_posicion_en_y(get_posicion_en_y() + velocidad_actual);   
    }

    //puntaje
    public int calcular_puntaje() { return 30; }
    public int calcular_penalizacion() { return 30; }    

    //destruir del mapa
    public void destruir(Mapa mapa) {
        if (!destruida) {
    		Juego.get_instancia().get_mapa_nivel_actual().animacion_puntaje_obtenido(
    				(int) this.get_posicion_en_x(), 
    				(int) this.get_posicion_en_y()-100, 
    				"+"+this.calcular_puntaje()
    				);
            destruida = true;
            mapa.eliminar_enemigo(this);
            eliminar_del_mapa();
        }
    }
    
    //colisiones
    
    //colision piranha y mario
    public boolean aceptar(EnemigosVisitor visitante) {
		return visitante.visitar(this);
	}

    //colisiones piranha y plataformas (piranha no colisiona)
	public void visitar(Vacio vacio) {}
	public void visitar(BloqueSolido bloque_solido) {}
	public void visitar(BloqueDePregunta bloque_de_pregunta) {}
	public void visitar(Tuberia tuberia) {}
	public void visitar(LadrilloSolido ladrillo_solido) {}

	//colisiones entre enemigos(piranha no colisiona con nadie)
	public void visitar_enemigo(Goomba goomba) {}
	public void visitar_enemigo(BuzzyBeetle buzzy) {}
	public void visitar_enemigo(KoopaTroopa koopa) {}
	public void visitar_enemigo(Lakitu lakitu) {}
	public void visitar_enemigo(PiranhaPlant piranha) {}
	public void visitar_enemigo(Spiny spiny) {}
	public void aceptar(EnemigoVisitorEnemigo visitante) {}
}