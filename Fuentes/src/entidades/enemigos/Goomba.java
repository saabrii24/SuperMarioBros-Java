package entidades.enemigos;

import entidades.Entidad;
import entidades.EntidadMovible;
import entidades.plataformas.BloqueDePregunta;
import entidades.plataformas.BloqueSolido;
import entidades.plataformas.LadrilloSolido;
import entidades.plataformas.Tuberias;
import entidades.plataformas.Vacio;
import fabricas.Sprite;
import logica.Mapa;
import logica.ResultadoColision;

public class Goomba extends Enemigo {
    public Goomba(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public void actualizar() {
        if (esta_cayendo()) {
            set_velocidad_en_y(get_velocidad_en_y() + GRAVEDAD);
            set_posicion_en_y(get_posicion_en_y() - get_velocidad_en_y());
        }
        set_posicion_en_x(get_posicion_en_x() + get_velocidad_en_x());
    }
    
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_goomba(this);
            eliminar_del_mapa();
        }
    }

    public int calcular_puntaje() { return 60; }

    public int calcular_penalizacion() { return 30; }

    public void mover() {
        switch (direccion) {
            case 1 -> mover_derecha();
            case -1 -> mover_izquierda();
            default -> detener_movimiento();
        }
    }

    protected void mover_izquierda() {
        set_velocidad_en_x(-1);
        movimiento_derecha = false;
    }

    protected void mover_derecha() {
        set_velocidad_en_x(1);
        set_movimiento_derecha(true);
        movimiento_derecha = true;
    }

    protected void detener_movimiento() {
        set_velocidad_en_x(0);
    }
   
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
		public void visitar_enemigo(Goomba goomba) {
			goomba.set_direccion(goomba.get_direccion()*-1);
			this.set_direccion(direccion);
		}


		@Override
		public void visitar_enemigo(BuzzyBeetle buzzy) {}


		@Override
		public void visitar_enemigo(KoopaTroopa koopa) {}


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