package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class ControladorDeEntradas {
    public void registrar_oyente_teclado(JPanel panel, ControladorDeVistas controlador) {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evento) {
                int keyCode = evento.getKeyCode();
                manejarTecla(keyCode, controlador);
            }
        });
        panel.setFocusable(true); 
        panel.requestFocusInWindow(); 
    }

    private void manejarTecla(int keyCode, ControladorDeVistas controlador) {
        System.out.println("Tecla presionada: " + KeyEvent.getKeyText(keyCode)); // Debugging
        switch (keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("Arriba presionado");
                controlador.panel_pantalla_principal.mover_seleccion(true);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Abajo presionado");
                controlador.panel_pantalla_principal.mover_seleccion(false);
                break;
            case KeyEvent.VK_ENTER:
                System.out.println("Enter presionado");
                controlador.panel_pantalla_principal.ejecutar_accion_seleccionada();
                break;
        }
    }

}
