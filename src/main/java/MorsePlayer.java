import javax.swing.*;

/**
 * Application entry point.
 *
 * @author Thibault Helsmoortel
 */
public class MorsePlayer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MorsePlayerView::new);
    }
}
