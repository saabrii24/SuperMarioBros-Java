package entidades.mario;

import entidades.Entidad;
import entidades.enemigos.Enemigo;
import entidades.powerups.PowerUp;
import fabricas.Sprite;

public class Mario extends Entidad{
    private MarioState estado;
    private static Mario instancia_mario;
    private int puntaje_nivel_actual;
    private int puntaje_acumulado;
    private int monedas;
    private static Sprite sprite;

    public Mario(int x, int y, Sprite sprite) {
    	super(x, y, sprite);
        this.puntaje_acumulado = 0;
        this.monedas = 0;
    }

    //Mario Singleton
    public static Mario get_instancia() {
        if (instancia_mario == null) {
            instancia_mario = new Mario(0, 0, sprite);
        }
        return instancia_mario;
    }

    //Mario State
    public interface MarioState {
        void consumir(PowerUp powerup);
        boolean matar_si_hay_colision(Enemigo enemigo);
    }

    public void saltar() {
    	
    }

    public void mover() {

    }

    public void consumir(PowerUp powerup) {
        estado.consumir(powerup);
    }

    public void cambiar_estado(MarioState nuevo_estado) {
        this.estado = nuevo_estado;
    }

    public boolean matar_si_hay_colision(Enemigo enemigo) {
        return estado.matar_si_hay_colision(enemigo);
    }

    public int get_puntaje_acumulado() {
        return puntaje_acumulado;
    }

    public int get_monedas() {
        return monedas;
    }

    public void set_puntaje_acumulado(int puntaje) {
        this.puntaje_acumulado = puntaje;
    }

    public void set_monedas(int monedas) {
        this.monedas = monedas;
    }

    public void actualizar_puntaje_nivel_actual(int puntos) {
        this.puntaje_nivel_actual = puntos;
    }

    public void sumar_puntaje_acumulado(int puntos) {
        this.puntaje_acumulado += puntos;
    }

    public void sumar_moneda() {
        this.monedas++;
    }

    public void resetear_puntaje_nivel_actual() {
        this.puntaje_nivel_actual = 0;
    }

    public void resetear_monedas() {
        this.monedas = 0;
    }
}
