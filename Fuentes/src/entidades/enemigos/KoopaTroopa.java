package entidades.enemigos;

import fabricas.Sprite;
import logica.Mapa;

public class KoopaTroopa extends Enemigo {
    protected KoopaState estado;

    public KoopaTroopa(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        estado = new NormalKoopaState(this);
    }

    public interface KoopaState {
        void actualizar_sprite();
        void mover();
        void cambiar_estado();
        boolean en_movimiento();
        boolean mata_tocando();
    }

    public void actualizar() {
        if (cayendo) velocidad_en_y += GRAVEDAD;
        set_posicion_en_y(get_posicion_en_y() - velocidad_en_y);
        set_posicion_en_x(get_posicion_en_x() + velocidad_en_x);
        actualizar_sprite();
    }

    public int calcular_puntaje() { return 90; }

    public int calcular_penalizacion() { return 45; }

    public void mover() { estado.mover(); }

    protected void mover_izquierda() { velocidad_en_x = -3; movimiento_derecha = false; }

    protected void mover_derecha() { velocidad_en_x = 3; movimiento_derecha = true; }

    protected void detener_movimiento() { velocidad_en_x = 0; }

    public void destruir(Mapa mapa) {
        if (!destruida) {
            mapa.reproducir_efecto("kick");
            destruida = true;
            mapa.eliminar_koopa_troopa(this);
            eliminar_del_mapa();
        }
    }

    private void actualizar_sprite() { estado.actualizar_sprite(); }

    public void cambiar_sprite(Sprite sprite) { this.sprite = sprite; }

    public void set_estado(KoopaState estado) { this.estado = estado; }

    public KoopaState get_estado() { return estado; }

    public boolean get_movimiento_derecha() { return movimiento_derecha; }

    public boolean en_movimiento() { return estado.en_movimiento(); }

    public boolean mata_tocando() { return estado.mata_tocando(); }

    public void cambiar_estado() { estado.cambiar_estado(); }
}