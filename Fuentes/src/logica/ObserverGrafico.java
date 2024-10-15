package logica;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// Abstraccion de los dos tipos de observadores disponibles (grafico y jugador)
// Encapsula todo el comportamiento en comun.

public abstract class ObserverGrafico extends JLabel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private EntidadLogica entidad_observada;
	
	protected ObserverGrafico(EntidadLogica entidad_observada) {
		super();
		this.entidad_observada = entidad_observada;
	}
	
	public void actualizar() {
		actualizar_imagen();
		actualizar_posicion_tamano();
	}
	
	protected void actualizar_imagen() {
	    String ruta_imagen = entidad_observada.get_sprite().get_ruta_imagen();
	    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta_imagen));

	    // Verifica si la imagen es un GIF animado
	    if (ruta_imagen.toLowerCase().endsWith(".gif")) {
	        // Mantener el GIF animado sin escalarlo
	        setIcon(iconoOriginal);
	    } else {
	        int nuevoAncho = iconoOriginal.getIconWidth() * 3; 
	        int nuevoAlto = iconoOriginal.getIconHeight() * 3; 
	        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

	        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
	        setIcon(iconoEscalado);
	    }
	}


	
	protected void actualizar_posicion_tamano() {
		int x = (int) AdaptadorPosicionPixel.transformar_x(entidad_observada.get_posicion_en_x());
		int y = (int) AdaptadorPosicionPixel.transformar_y(entidad_observada.get_posicion_en_y());
		int ancho = this.getIcon().getIconWidth();
		int alto = this.getIcon().getIconHeight();
		setBounds(x, y, ancho, alto);
	}
}
