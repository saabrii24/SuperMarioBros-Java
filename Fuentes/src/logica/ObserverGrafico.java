package logica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import entidades.interfaces.EntidadLogica;

public abstract class ObserverGrafico extends JLabel implements Observer {

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
    
    public void notificar_destruir() {
        setIcon(null); 
        setVisible(false); 
    }
    
    protected void actualizar_imagen() {
        if (entidad_observada.get_sprite() == null) { return; }
        String ruta_imagen = entidad_observada.get_sprite().get_ruta_imagen();
        ImageIcon icono_original = new ImageIcon(getClass().getResource(ruta_imagen));
        boolean es_un_gif = ruta_imagen.toLowerCase().endsWith(".gif");
        if (es_un_gif) {
            setIcon(icono_original);
            
            entidad_observada.get_sprite().set_ancho(icono_original.getIconWidth());
            entidad_observada.get_sprite().set_alto(icono_original.getIconHeight());
            entidad_observada.set_dimension(icono_original.getIconWidth(), icono_original.getIconHeight());
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
    	if (this.getIcon() == null) { return; }
    	int x = (int) AdaptadorPosicionPixel.transformar_x(entidad_observada.get_posicion_en_x());
        int y = (int) AdaptadorPosicionPixel.transformar_y(entidad_observada.get_posicion_en_y());
        int ancho = this.getIcon().getIconWidth();
        int alto = this.getIcon().getIconHeight();
        setBounds(x, y, ancho, alto);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Llama al método original para pintar la imagen

        // Dibuja el contorno del rectángulo completo alrededor de la entidad
        g.setColor(Color.RED);
        Rectangle contorno = entidad_observada.get_limites();
        g.drawRect((int) (contorno.x - entidad_observada.get_posicion_en_x()), 
                   (int) (contorno.y - entidad_observada.get_posicion_en_y()), 
                   contorno.width, contorno.height);

        // Dibuja los rectángulos de colisión
        g.setColor(Color.BLUE);
        Rectangle inferiores = entidad_observada.get_limites_inferiores();
        g.drawRect((int) (inferiores.x - entidad_observada.get_posicion_en_x()), 
                   (int) (inferiores.y - entidad_observada.get_posicion_en_y()), 
                   inferiores.width, inferiores.height);

        g.setColor(Color.GREEN);
        Rectangle superiores = entidad_observada.get_limites_superiores();
        g.drawRect((int) (superiores.x - entidad_observada.get_posicion_en_x()), 
                   (int) (superiores.y - entidad_observada.get_posicion_en_y()), 
                   superiores.width, superiores.height);

        g.setColor(Color.YELLOW);
        Rectangle izquierda = entidad_observada.get_limites_izquierda();
        g.drawRect((int) (izquierda.x - entidad_observada.get_posicion_en_x()), 
                   (int) (izquierda.y - entidad_observada.get_posicion_en_y()), 
                   izquierda.width, izquierda.height);

        g.setColor(Color.MAGENTA);
        Rectangle derecha = entidad_observada.get_limites_derecha();
        g.drawRect((int) (derecha.x - entidad_observada.get_posicion_en_x()), 
                   (int) (derecha.y - entidad_observada.get_posicion_en_y()), 
                   derecha.width, derecha.height);
    }

}
