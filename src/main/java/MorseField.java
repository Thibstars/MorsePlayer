import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class extending {@link JTextArea} allowing only Morse input.
 *
 * @author Thibault Helmsoortel
 */
class MorseField extends JTextArea {

    MorseField() {
        ((AbstractDocument) getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

                StringBuilder sb = new StringBuilder(string);
                for (int i = sb.length() - 1; i >= 0; i--) {
                    char ch = sb.charAt(i);
                    if (ch != '/' && ch != '.' && ch != '-') {
                        sb.deleteCharAt(i);
                    }
                }
                super.insertString(fb, offset, sb.toString(), attr);
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb,
                                int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                if (length > 0) fb.remove(offset, length);
                insertString(fb, offset, string, attr);
            }
        });
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    void addEnterAction(Runnable runnable) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    runnable.run();
                }
            }
        });
    }
}
