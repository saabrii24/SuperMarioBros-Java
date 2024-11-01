package gui;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.BufferedInputStream;

public class Sonido {
    private Clip musica_de_fondo;
    private boolean sonido_activo;
    private long tiempo_pausado_en_ms;

    public Sonido() {
        this.sonido_activo = true;
        this.tiempo_pausado_en_ms = 0;
    }

    public void reproducir_musica_de_fondo() {
        if (!sonido_activo) return;
        
        try {
            if (musica_de_fondo == null || !musica_de_fondo.isOpen()) {
                BufferedInputStream buffered_stream = new BufferedInputStream(
                    getClass().getResourceAsStream("/assets/sonido/background.wav")
                );
                AudioInputStream audio_stream = AudioSystem.getAudioInputStream(buffered_stream);
                musica_de_fondo = AudioSystem.getClip();
                musica_de_fondo.open(audio_stream);
                musica_de_fondo.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                musica_de_fondo.setMicrosecondPosition(tiempo_pausado_en_ms);
                musica_de_fondo.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void pausar_musica_de_fondo() {
        if (musica_de_fondo != null && musica_de_fondo.isRunning()) {
        	tiempo_pausado_en_ms = musica_de_fondo.getMicrosecondPosition();
            musica_de_fondo.stop();
        }
    }

    public void detener_musica_de_fondo() {
        if (musica_de_fondo != null) {
            musica_de_fondo.stop();
            musica_de_fondo.close();
            tiempo_pausado_en_ms = 0;
        }
    }

    public void reproducir_efecto(String efecto) {
        if (!sonido_activo) return;

        try {
            BufferedInputStream buffered_stream = new BufferedInputStream(
                getClass().getResourceAsStream("/assets/sonido/smb_" + efecto + ".wav")
            );
            AudioInputStream audio_stream = AudioSystem.getAudioInputStream(buffered_stream);
            Clip efecto_sonido = AudioSystem.getClip();
            efecto_sonido.open(audio_stream);
            efecto_sonido.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activar_sonido() {
        sonido_activo = true;
        reproducir_musica_de_fondo();
    }

    public void desactivar_sonido() {
        sonido_activo = false;
        detener_musica_de_fondo();
    }
}