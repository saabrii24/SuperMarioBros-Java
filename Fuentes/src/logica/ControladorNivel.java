package logica;

import java.util.ArrayList;
import java.util.List;

import entidades.Entidad;
import entidades.mario.Mario;
import fabricas.EntidadesFactory;
import niveles.GeneradorNivel;

public class ControladorNivel {
    private final Juego juego;
    private boolean reiniciando_nivel;
    private int nivel_actual;
    
    public ControladorNivel(Juego juego) {
        this.juego = juego;
        this.nivel_actual = 1;
    }

    public void cargar_datos(EntidadesFactory generador) {
    	juego.get_mapa_nivel_actual().barrer_mapa();
        juego.nivel_actual = GeneradorNivel.cargar_nivel_y_mapa(
            getClass().getResourceAsStream("/niveles/nivel-" + nivel_actual + ".txt"),
            generador,
            juego.mapa_nivel_actual
        );

        juego.fabrica_entidades = generador;
        registrar_observers();
        juego.iniciar_hilos_movimiento();
    }

    public void reiniciar_datos_nivel() {
        reiniciando_nivel = true;
        
        try {
            cargar_datos(juego.fabrica_entidades);
            resetear_mario();

            juego.get_mapa_nivel_actual().get_colisionador().set_murio_mario(false);
            juego.controlador_vistas.refrescar();
            
        } finally {
            reiniciando_nivel = false;
        }
    }

    private void resetear_mario() {
        Mario mario = Mario.get_instancia();
        mario.resetear_posicion();
        juego.get_mapa_nivel_actual().agregar_mario(mario);
    }

    private void registrar_observers() {
        registrar_observer_jugador(Mario.get_instancia());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_bloque_de_pregunta());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_bloque_solido());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_ladrillo_solido());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_powerup());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_tuberias());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_vacio());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_buzzy_beetle());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_goomba());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_koopa_troopa());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_lakitu());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_piranha_plant());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_spiny());
        registrar_observers_para_entidades(juego.get_mapa_nivel_actual().get_entidades_proyectiles());
    }

    private void registrar_observer_jugador(Mario mario) {
        Observer observer = juego.controlador_vistas.registrar_entidad(mario);
        mario.registrar_observer(observer);
    }

    private void registrar_observers_para_entidades(List<? extends Entidad> entidades) {
        List<? extends Entidad> entidades_copia = new ArrayList<>(entidades);
        for (Entidad entidad : entidades_copia) {
            synchronized (entidad) {
                Observer observer = juego.controlador_vistas.registrar_entidad(entidad);
                entidad.registrar_observer(observer);
            }
        }
    }

    public boolean esta_reiniciando_nivel() {
        return reiniciando_nivel;
    }

    public void incrementar_nivel() {
    	
        Juego.get_instancia().reproducir_efecto("stage_clear");
        Mario.get_instancia().get_sistema_puntuacion().pasar_nivel();
        

        if(nivel_actual <= 3)
        	cargar_datos(juego.fabrica_entidades);
    }

    public int get_nivel_actual() {
        return nivel_actual;
    }

	public void set_nivel_actual(int nivel_actual) {
		this.nivel_actual = nivel_actual;
	}
}
