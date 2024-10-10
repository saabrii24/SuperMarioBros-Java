package logica;

import gui.ConstantesVistas;

public class AdaptadorPosicionPixel {
	
	public static double transformar_x(double d) {
		return d;
	}
	
	// Las coordenadas gráficas se miden de arriba hacia abajo
	public static double transformar_y(double d) {
		return ConstantesVistas.PANEL_ALTO - d;
	}
}
