package logica;

import java.awt.Image;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ranking.*;

public class ControladorRanking implements Serializable {

    private static final long serialVersionUID = -6203851574103194125L;
    private final String ruta_archivo;
    private Ranking ranking;
    private Juego juego;

    public ControladorRanking(Juego juego) {
        this("./src/ranking/ranking.tdp");
        this.juego = juego;
        cargar_ranking();
    }

    public ControladorRanking(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
        cargar_ranking();
        if (this.ranking == null) {
            this.ranking = new Ranking();
        }
    }

    public Ranking get_ranking() {
        return ranking;
    }

    public void set_ranking(Ranking ranking) {
        this.ranking = ranking;
    }

    private boolean puede_entrar_en_ranking(int puntaje) {
        if (puntaje <= 0) {
            return false;
        }

        if (ranking.get_ranking().size() < 5) { 
            return true;
        }
        
        Jugador jugador_menor = ranking.get_ranking().get(ranking.get_ranking().size() - 1);
        return puntaje > jugador_menor.get_puntos();
    }

    public void actualizar_ranking(int puntaje) {
        if (ranking == null) {
            ranking = new Ranking();
        }

        if (puede_entrar_en_ranking(puntaje)) {
            String nombre_jugador = solicitar_nombre_jugador();
            ranking.agregar_jugador(nombre_jugador, puntaje);
            guardar_ranking();

            if (juego != null && juego.get_controlador_vistas() != null) {
                juego.get_controlador_vistas().accionar_pantalla_ranking();
            }
        }
    }

    // Método para deserializar el ranking (cargar desde archivo)
    public void cargar_ranking() {
        File file = new File(ruta_archivo);
        if (file.exists()) {
            try (FileInputStream file_input_stream = new FileInputStream(ruta_archivo);
                 ObjectInputStream object_input_stream = new ObjectInputStream(file_input_stream)) {
                set_ranking((Ranking) object_input_stream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.ranking = new Ranking();
            guardar_ranking();
        }
    }

    // Método para serializar el ranking (guardar en archivo)
    private void guardar_ranking() {
        try (FileOutputStream file_output_stream = new FileOutputStream(ruta_archivo);
             ObjectOutputStream object_output_stream = new ObjectOutputStream(file_output_stream)) {
        	object_output_stream.writeObject(ranking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String solicitar_nombre_jugador() {
        ImageIcon icono_mario = new ImageIcon(this.getClass().getResource("/assets/imagenes/icon.png"));
        Image imagen_escalada = icono_mario.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon icono_mario_chiquito = new ImageIcon(imagen_escalada);

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setLocation(frame.getX(), frame.getY() - 150);
        frame.setVisible(true);

        String nombre_jugador = (String) JOptionPane.showInputDialog(
            frame,
            "Ingrese su nombre:",
            "Nombre del Jugador",
            JOptionPane.PLAIN_MESSAGE,
            icono_mario_chiquito,
            null,
            null
        );

        frame.dispose();

        if (nombre_jugador == null || nombre_jugador.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            return solicitar_nombre_jugador(); // Pide el nombre nuevamente
        }
        return nombre_jugador;
    }

}
