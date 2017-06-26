import lombok.extern.log4j.Log4j2;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Class providing means to play sounds as defined in {@link Sound}.
 *
 * @author Thibault Helsmoortel
 */
@Log4j2
class SoundPlayer {

    private static final int BUFFER_SIZE = 128000;
    private static AudioInputStream audioStream;
    private static SourceDataLine sourceLine;

    private static void playPause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    static void playSound(Sound sound) {
        switch (sound) {
            case NONE:
                playPause(500);
                break;
            case SHORT_BEEP:
                playSound(SoundPlayer.class.getClassLoader().getResource("sound/short.wav"));
                break;
            case LONG_BEEP:
                playSound(SoundPlayer.class.getClassLoader().getResource("sound/long.wav"));
                break;
        }
    }

    private static void playSound(URL url) {
        try {
            audioStream = AudioSystem.getAudioInputStream(new URL(url.toString().replaceFirst("file:/", "file:///")));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }

        AudioFormat audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();

        playPause(250);
    }
}
