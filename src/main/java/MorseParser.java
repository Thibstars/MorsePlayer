import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for parsing Morse sequences into sound sequences.
 *
 * @author Thibault Helsmoortel
 */
final class MorseParser {

    static List<Sound> parse(String morse) {
        char[] chars = morse.toCharArray();
        List<Sound> soundList = new ArrayList<>(chars.length);
        for (char c : chars) {
            if (c == '/') {
                soundList.add(Sound.NONE);
            } else if (c == '.') {
                soundList.add(Sound.SHORT_BEEP);
            } else if (c == '-') {
                soundList.add(Sound.LONG_BEEP);
            }
        }
        return soundList;
    }
}
