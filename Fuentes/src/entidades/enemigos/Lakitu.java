package entidades.enemigos;

import entidades.interfaces.EnemigosVisitor;
import entidades.plataformas.*;
import entidades.interfaces.EnemigoVisitorEnemigo;
import fabricas.Sprite;
import logica.Juego;
import logica.Mapa;

public class Lakitu extends Enemigo{
	private long tiempo_ultimo_spiny = 0;
	private static final long SPINY_COOLDOWN = 3000000000L;

	public Lakitu(int x, int y, Sprite sprite) {super(x, y, sprite);}

	//movimiento
	public void mover() {}

	//actualizar
	public void actualizar() {
        long tiempo_actual = System.nanoTime();
        if (tiempo_actual - tiempo_ultimo_spiny >= SPINY_COOLDOWN) {
            Mapa mapa = Juego.get_instancia().get_mapa_nivel_actual();
            if (mapa != null) {
                lanzar_spiny(mapa);
                tiempo_ultimo_spiny = tiempo_actual;
            }
        }
    }
	
	//puntaje
    public int calcular_puntaje() {return 60;}
    public int calcular_penalizacion() {return 0;}
    
    //destruir del mapa
	public void destruir(Mapa mapa) {
        if (!destruida) {
        	mapa.reproducir_efecto("kick");
            destruida = true;           
            mapa.eliminar_enemigo(this);
            eliminar_del_mapa();
        }
    }

	public void lanzar_spiny(Mapa mapa) {
		Spiny spiny = new Spiny ((int) this.get_posicion_en_x() + 10, (int )this.get_posicion_en_y(), Juego.get_instancia().get_fabrica_sprites().get_spiny_movimiento_izquierda());
		mapa.agregar_enemigo(spiny);	
		spiny.registrar_observer(Juego.get_instancia().get_controlador_vistas().registrar_entidad(spiny));
	}

	//Colisiones
	
	//colision entre lakitu y mario
	public boolean aceptar(EnemigosVisitor visitador) {
		return visitador.visitar(this);
	}

	//colision entre lakitu y plataformas(lakitu no colisiona)
	public void visitar(Vacio vacio) {}
	public void visitar(BloqueSolido bloque_solido) {}
	public void visitar(BloqueDePregunta bloque_de_pregunta) {}
	public void visitar(Tuberias tuberia) {}
	public void visitar(LadrilloSolido ladrillo_solido) {}
	
	//colision entre lakitu y enemigos(lakitu no colisiona)
	public void visitar_enemigo(Goomba goomba) {}
	public void visitar_enemigo(BuzzyBeetle buzzy) {}
	public void visitar_enemigo(KoopaTroopa koopa) {}
	public void visitar_enemigo(Lakitu lakitu) {}
	public void visitar_enemigo(PiranhaPlant piranha) {}
	public void visitar_enemigo(Spiny spiny) {}
	public void aceptar(EnemigoVisitorEnemigo visitador) {}
}