package logica;

import java.awt.Graphics;

public interface Observer {
    public void actualizar();
    public void notificar_destruir();
	public void paintComponent(Graphics g);
}

