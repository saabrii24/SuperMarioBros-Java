package entidades.enemigos;
import fabricas.Sprite;
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

    @Override
    public void mover() {}

    @Override
    public void actualizar() {
        this.cayendo = false;
        set_velocidad_en_y(0);
        cambiar_direccion();
        set_posicion_en_y(get_posicion_en_y() + velocidad_actual);   
    }

    public int calcular_puntaje() { return 30; }

    public int calcular_penalizacion() { return 30; }    
    
    @Override
    public boolean esta_cayendo() {
        return false;
    }
    
    @Override
    public void set_cayendo(boolean esta_cayendo) {
        this.cayendo = false;
    }

    @Override
    public void set_velocidad_en_y(double vy) {
        super.set_velocidad_en_y(0);
    }

    public void destruir(Mapa mapa) {
        if (!destruida) {
            mapa.reproducir_efecto("kick");
            destruida = true;
            mapa.eliminar_piranha_plant(this);
            eliminar_del_mapa();
        }
    }
    
    public int aceptar(EnemigosVisitor visitador) {
		return visitador.visitar(this);
	}
}