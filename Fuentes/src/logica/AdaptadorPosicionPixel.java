package logica;

import gui.ConstantesVistas;

/**
 * La clase {@code AdaptadorPosicionPixel} invierte el eje Y.
 * Esto es importante al tener en cuenta cálculos de gravedad y aplicados en torno al eje Y.
 */
public class AdaptadorPosicionPixel {
	
	public static double transformar_x(double x) { return x; }
	
	// Las coordenadas gráficas se miden de arriba hacia abajo
	public static double transformar_y(double y) { return ConstantesVistas.PANEL_ALTO - y; }
	
}
