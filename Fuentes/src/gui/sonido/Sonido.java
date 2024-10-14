package gui.sonido;

public class Sonido {
	
	private SonidoState estado;
	
	public interface SonidoState {
	    void reproducir_musica_de_fondo();
	    void detener_musica_de_fondo();
	    void reproducir_efecto_sonido(String efecto);
	}

	public Sonido() {
        this.estado = new OnMusicState(); 
    }

    public void cambiarEstado(SonidoState nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void activarSonido() {
        cambiarEstado(new OnMusicState());
        estado.reproducir_musica_de_fondo();
    }

    public void desactivarSonido() {
    	estado.detener_musica_de_fondo();
        cambiarEstado(new OffMusicState());
    }

    public void reproducirEfecto(String efecto) {
        estado.reproducir_efecto_sonido(efecto);
    }
}
