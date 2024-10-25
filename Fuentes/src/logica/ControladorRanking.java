package logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import ranking.Ranking;

public class ControladorRanking {
    private final String ruta_archivo;
    
    public ControladorRanking() {
        this("./ranking.tdp"); // ruta por defecto
    }
    
    public ControladorRanking(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    public void actualizar_ranking(int puntaje) {
        Ranking ranking = cargar_ranking();
        String nombre_jugador = solicitar_nombre_jugador();
        ranking.agregar_jugador(nombre_jugador, puntaje);
        guardar_ranking(ranking);
    }

    private Ranking cargar_ranking() {
        Ranking ranking = new Ranking();
        try (FileInputStream file_input_stream = new FileInputStream(ruta_archivo);
             ObjectInputStream object_input_stream = new ObjectInputStream(file_input_stream)) {
            ranking = (Ranking) object_input_stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    private String solicitar_nombre_jugador() {
        String nombre_jugador = "";
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese su nombre: ");
            nombre_jugador = scanner.next();
        }
        return nombre_jugador;
    }

    private void guardar_ranking(Ranking ranking) {
        try (FileOutputStream file_output_stream = new FileOutputStream(ruta_archivo);
             ObjectOutputStream object_output_stream = new ObjectOutputStream(file_output_stream)) {
            object_output_stream.writeObject(ranking);
            object_output_stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
