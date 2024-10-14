package gui.sonido;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import gui.sonido.Sonido.SonidoState;

import java.io.File;

public class OnMusicState implements SonidoState {
    private Clip musicaDeFondo;

    public void reproducir_musica_de_fondo() {
        try {
            File archivoMusica = new File("/assets/sonido/background.wav");
            musicaDeFondo = AudioSystem.getClip();
            musicaDeFondo.open(AudioSystem.getAudioInputStream(archivoMusica));
            musicaDeFondo.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detener_musica_de_fondo() {
        if (musicaDeFondo != null && musicaDeFondo.isRunning()) {
            musicaDeFondo.stop();
            musicaDeFondo.close();
        }
    }

    public void reproducir_efecto_sonido(String efecto) {
        try {
            File archivoEfecto = new File("/assets/sonido/smb_" + efecto + ".wav");
            Clip efectoSonido = AudioSystem.getClip();
            efectoSonido.open(AudioSystem.getAudioInputStream(archivoEfecto));
            efectoSonido.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

