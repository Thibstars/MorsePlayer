import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;

/**
 * Main application frame.
 *
 * @author Thibault Helsmoortel
 */
@Log4j2
class MorsePlayerView extends JFrame {

    private MorseField morseField;
    private JButton btnPlay;

    MorsePlayerView() throws HeadlessException {
        super("MorsePlayer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(250, 150);
        setSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setLocationRelativeTo(null);
        initLayout();
        addListeners();
        setVisible(true);
    }

    private void addListeners() {
        morseField.addEnterAction(this::playMorse);
        btnPlay.addActionListener(e -> playMorse());
    }

    private void playMorse() {
        java.util.List<Sound> soundList = MorseParser.parse(morseField.getText());
        soundList.forEach(sound -> {
            log.debug("Playing sound: {}.", sound);
            SoundPlayer.playSound(sound);
        });
    }

    private void initLayout() {
        morseField = new MorseField();
        morseField.setFont(new Font("sans-serif", Font.PLAIN, 18));
        btnPlay = new JButton("Play!");

        add(morseField, BorderLayout.CENTER);
        add(btnPlay, BorderLayout.PAGE_END);
    }
}
