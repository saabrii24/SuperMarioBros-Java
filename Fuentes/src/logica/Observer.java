package logica;

import java.awt.Graphics;

public interface Observer {
    public void actualizar();
    public void notificar_eliminar_del_mapa();
	public void paintComponent(Graphics g);
}

