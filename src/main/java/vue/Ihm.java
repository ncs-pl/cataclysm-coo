package vue;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Interface homme-machine avec le terminal utilisateur (stdin, stdout, stderr). */
public class Ihm {
    /** Scanner réutilisable pour les entrées utilisateurs. */
    private final Scanner scanner;
    /** Sortie système pour afficher des informations à l'utilisateur. */
    private final PrintStream output;
    /** Sortie système pour avertir des erreurs à l'utilisateur. */
    private final PrintStream error;

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

    /**
     * Créer une interface homme-machine.
     * @param input - l'entrée système pour obtenir des entrées utilisateurs.
     * @param output - sortie système pour afficher des informations à l'utilisateur.
     * @param error - sortie système pour avertir des erreurs à l'utilisateur.
     */
    public Ihm(InputStream input, PrintStream output, PrintStream error) {
        assert(input != null);
        assert(output != null);
        assert(error != null);

        this.scanner = new Scanner(input);
        this.output = output;
        this.error = error;
    }

    /**
     * Affiche un message d'information sur la sortie système d'informations.
     * @param message - Le message à afficher.
     */
    public void afficherInformation(String message) {
        assert(message != null);
        assert(!message.isEmpty());

        this.output.println(Ihm.COLOR_CYAN + "[INF] " + message + Ihm.COLOR_RESET);
    }

    /**
     * Affiche une erreur sur la sortie système d'erreurs.
     * @param exception - l'erreur à afficher.
     */
    public void afficherErreur(Exception exception) {
        assert(exception != null);
        this.error.println(Ihm.COLOR_RED + "[ERR] " + exception.getMessage() + Ihm.COLOR_RESET);
    }

    /**
     * Pose une question à l'utilisateur et attend un String en réponse.
     * @param question - la question à poser.
     * @return la réponse de l'utilisateur.
     */
    public String demanderString(String question) {
        assert(question != null);
        assert(!question.isEmpty());

        this.output.println(Ihm.COLOR_YELLOW + question + Ihm.COLOR_RESET);
        this.output.print(Ihm.COLOR_YELLOW + "> " + Ihm.COLOR_RESET);
        return(this.scanner.nextLine());
    }

    /**
     * Affiche un contenu directement sur la sortie système d'information, sans ajouter de décoration.
     * Utile pour afficher le contenu ou l'état du jeu depuis le contrôleur tout en restant mettre de l'affichage.
     * @param message - le message brut.
     */
    public void afficherMessageBrut(String message) {
        this.output.println(message);
    }
}
