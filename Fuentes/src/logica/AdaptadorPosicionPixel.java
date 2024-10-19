package logica;

import gui.ConstantesVistas;

public class AdaptadorPosicionPixel {
	
	public static double transformar_x(double x) {
		return x;
	}
	
	// Las coordenadas gráficas se miden de arriba hacia abajo
	public static double transformar_y(double y) {
		return ConstantesVistas.PANEL_ALTO - y;
	}
}
