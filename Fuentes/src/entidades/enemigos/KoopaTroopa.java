package entidades.enemigos;

import entidades.Entidad;
import entidades.EntidadMovible;
import entidades.plataformas.BloqueDePregunta;
import entidades.plataformas.BloqueSolido;
import entidades.plataformas.LadrilloSolido;
import entidades.plataformas.Plataforma;
import entidades.plataformas.Tuberias;
import entidades.plataformas.Vacio;
import fabricas.Sprite;
import logica.Mapa;
import logica.ResultadoColision;

public class KoopaTroopa extends Enemigo {
    protected KoopaState estado;

    public KoopaTroopa(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        estado = new NormalKoopaState(this);
    }

    public interface KoopaState {
        void actualizar_sprite();
        void mover();
        ResultadoColision cambiar_estado();
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

    public ResultadoColision cambiar_estado() { return estado.cambiar_estado(); }
    
    public ResultadoColision aceptar(EnemigosVisitor visitador) {
		return visitador.visitar(this);
	}
    
    @Override
	public void visitar(Vacio vacio) {
		this.eliminar_del_mapa();
	}


	@Override
	public void visitar(BloqueSolido bloque_solido) {
		ajustar_posicion_enemigo_sobre_plataforma(this,bloque_solido);
	}


	@Override
	public void visitar(BloqueDePregunta bloque_de_pregunta) {
		ajustar_posicion_enemigo_sobre_plataforma(this,bloque_de_pregunta);
	}


	@Override
	public void visitar(Tuberias tuberia) {
		ajustar_posicion_enemigo_sobre_plataforma(this,tuberia);
	}


	@Override
	public void visitar(LadrilloSolido ladrillo_solido) {
		ajustar_posicion_enemigo_sobre_plataforma(this,ladrillo_solido);
	}
	
	 private void ajustar_posicion_enemigo_sobre_plataforma(EntidadMovible enemigo, Entidad plataforma) {
	        enemigo.set_velocidad_en_y(0);
	        enemigo.set_posicion_en_y(plataforma.get_posicion_en_y() + plataforma.get_dimension().height);
	 }	
	 
	 @Override
		public void visitar_enemigo(Goomba goomba) {}


		@Override
		public void visitar_enemigo(BuzzyBeetle buzzy) {}


		@Override
		public void visitar_enemigo(KoopaTroopa koopa) {
			koopa.set_direccion(koopa.get_direccion()*-1);
			this.set_direccion(direccion);
		}


		@Override
		public void visitar_enemigo(Lakitu lakitu) {}


		@Override
		public void visitar_enemigo(PiranhaPlant piranha) {}


		@Override
		public void visitar_enemigo(Spiny spiny) {}


		@Override
		public void aceptar(EnemigoVisitorEnemigo visitador) {
			visitador.visitar_enemigo(this);
		}	
}