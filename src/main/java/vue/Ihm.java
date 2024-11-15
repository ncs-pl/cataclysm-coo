package vue;

public class Ihm {
    // voir https://en.wikipedia.org/wiki/ANSI_escape_code#Colors

    /** Réinitialise la couleur du texte dans terminal à celle par défaut. */
    public static final String COLOR_RESET = "\u001B[0m";

    /** Change la couleur du texte dans le terminal en noir. */
    public static final String COLOR_BLACK = "\u001B[30m";

    /** Change la couleur du texte dans le terminal en rouge. */
    public static final String COLOR_RED = "\u001B[31m";

    /** Change la couleur du texte dans le terminal en vert. */
    public static final String COLOR_GREEN = "\u001B[32m";

    /** Change la couleur du texte dans le terminal en jaune. */
    public static final String COLOR_YELLOW = "\u001B[33m";

    /** Change la couleur du texte dans le terminal en bleu. */
    public static final String COLOR_BLUE = "\u001B[34m";

    /** Change la couleur du texte dans le terminal en violet. */
    public static final String COLOR_PURPLE = "\u001B[35m";

    /** Change la couleur du texte dans le terminal en cyan. */
    public static final String COLOR_CYAN = "\u001B[36m";

    /** Change la couleur du texte dans le terminal en blanc. */
    public static final String COLOR_WHITE = "\u001B[37m";

    /** Change la couleur de fond du texte dans le terminal en noir. */
    public static final String COLOR_BACKGROUND_BLACK = "\u001B[40m";

    /** Change la couleur de fond du texte dans le terminal en rouge. */
    public static final String COLOR_BACKGROUND_RED = "\u001B[41m";

    /** Change la couleur de fond du texte dans le terminal en vert. */
    public static final String COLOR_BACKGROUND_GREEN = "\u001B[42m";

    /** Change la couleur de fond du texte dans le terminal en jaune. */
    public static final String COLOR_BACKGROUND_YELLOW = "\u001B[43m";

    /** Change la couleur de fond du texte dans le terminal en bleu. */
    public static final String COLOR_BACKGROUND_BLUE = "\u001B[44m";

    /** Change la couleur de fond du texte dans le terminal en violet. */
    public static final String COLOR_BACKGROUND_PURPLE = "\u001B[45m";

    /** Change la couleur de fond du texte dans le terminal en cyan. */
    public static final String COLOR_BACKGROUND_CYAN = "\u001B[46m";

    /** Change la couleur de fond du texte dans le terminal en blanc. */
    public static final String COLOR_BACKGROUND_WHITE = "\u001B[47m";
}
