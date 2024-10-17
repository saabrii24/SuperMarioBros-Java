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
	    ImageIcon icono_original = new ImageIcon(getClass().getResource(ruta_imagen));

	    // Verifica si la imagen es un GIF animado
	    if (ruta_imagen.toLowerCase().endsWith(".gif")) {
	        // Mantener el GIF animado sin escalarlo
	        setIcon(icono_original);
	        
	        entidad_observada.get_sprite().set_ancho(icono_original.getIconWidth());
	        entidad_observada.get_sprite().set_alto(icono_original.getIconWidth());
	        entidad_observada.set_dimension(icono_original.getIconWidth(), icono_original.getIconWidth());
	    } else {
	        int nuevo_ancho = icono_original.getIconWidth() * 3; 

	        int nuevo_alto = icono_original.getIconHeight() * 3; 
	        Image imagen_escalada = icono_original.getImage().getScaledInstance(nuevo_ancho, nuevo_alto, Image.SCALE_SMOOTH);
	        
	        ImageIcon icono_escalado = new ImageIcon(imagen_escalada);
	        setIcon(icono_escalado);
	        
	        entidad_observada.get_sprite().set_ancho(nuevo_ancho);
	        entidad_observada.get_sprite().set_alto(nuevo_alto);
	        entidad_observada.set_dimension(nuevo_ancho, nuevo_alto);
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