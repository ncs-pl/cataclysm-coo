package vue;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Interface homme-machine avec le terminal utilisateur (stdin, stdout, stderr). */
public class Ihm {
    // Voir https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
    @SuppressWarnings("unused") public static final String COLOR_RESET = "\u001B[0m";
    @SuppressWarnings("unused") public static final String COLOR_BLACK = "\u001B[30m";
    @SuppressWarnings("unused") public static final String COLOR_RED = "\u001B[31m";
    @SuppressWarnings("unused") public static final String COLOR_GREEN = "\u001B[32m";
    @SuppressWarnings("unused") public static final String COLOR_YELLOW = "\u001B[33m";
    @SuppressWarnings("unused") public static final String COLOR_BLUE = "\u001B[34m";
    @SuppressWarnings("unused") public static final String COLOR_PURPLE = "\u001B[35m";
    @SuppressWarnings("unused") public static final String COLOR_CYAN = "\u001B[36m";
    @SuppressWarnings("unused") public static final String COLOR_WHITE = "\u001B[37m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_BLACK = "\u001B[40m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_RED = "\u001B[41m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_GREEN = "\u001B[42m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_YELLOW = "\u001B[43m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_BLUE = "\u001B[44m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_PURPLE = "\u001B[45m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_CYAN = "\u001B[46m";
    @SuppressWarnings("unused") public static final String COLOR_BACKGROUND_WHITE = "\u001B[47m";

    private final Scanner scanner;    // Entrée système.
    private final PrintStream output; // Sortie système pour les informations.
    private final PrintStream error;  // Sortie système pour les erreurs.

    public Ihm(InputStream input, PrintStream output, PrintStream error) {
        assert(input != null);
        assert(output != null);
        assert(error != null);

        this.scanner = new Scanner(input);
        this.output = output;
        this.error = error;
    }

    public void afficherInformation(String message) {
        this.output.println(Ihm.COLOR_CYAN + "[INFO] " + message + Ihm.COLOR_RESET);
    }

    public void afficherErreur(String erreur) {
        this.error.println(Ihm.COLOR_RED + "[ERREUR] " + erreur + Ihm.COLOR_RESET);
    }

    public String demanderString(String question) {
        // NOTE(nico): Pour éviter un overwrite de stdout
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            this.afficherErreur(e.getMessage());
        }

        this.output.println(Ihm.COLOR_YELLOW + question + Ihm.COLOR_RESET);
        this.output.print(Ihm.COLOR_YELLOW + "> " + Ihm.COLOR_RESET);
        return this.scanner.nextLine();
    }


    public int demanderInt(String question) {
        // NOTE(nico): Pour éviter un overwrite de stdout
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            this.afficherErreur(e.getMessage());
        }

        this.output.println(Ihm.COLOR_YELLOW + question + Ihm.COLOR_RESET);
        this.output.print(Ihm.COLOR_YELLOW + "> " + Ihm.COLOR_RESET);
        // TODO(nico): probablement mieux à faire !
        while (true) {
            if (this.scanner.hasNextInt()) {
                return this.scanner.nextInt();
            }

            afficherErreur("Valeur entrée non-numérique.");
            scanner.nextLine();
        }
    }
}
