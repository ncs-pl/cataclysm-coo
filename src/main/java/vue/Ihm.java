package vue;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Interface homme-machine avec le terminal utilisateur (stdin, stdout, stderr). */
@SuppressWarnings("unused")
public class Ihm {
    // Voir https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
    public static final String COULEUR_REINITIALISATION = "\u001B[0m";
    public static final String COULEUR_NOIR             = "\u001B[30m";
    public static final String COULEUR_ROUGE            = "\u001B[31m";
    public static final String COULEUR_VERT             = "\u001B[32m";
    public static final String COULEUR_JAUNE            = "\u001B[33m";
    public static final String COULEUR_BLEU             = "\u001B[34m";
    public static final String COULEUR_VIOLET           = "\u001B[35m";
    public static final String COULEUR_CYAN             = "\u001B[36m";
    public static final String COULEUR_BLANC            = "\u001B[37m";
    public static final String COULEUR_FOND_NOIR        = "\u001B[40m";
    public static final String COULEUR_FOND_ROUGE       = "\u001B[41m";
    public static final String COULEUR_FOND_VERT        = "\u001B[42m";
    public static final String COULEUR_FOND_JAUNE       = "\u001B[43m";
    public static final String COULEUR_FOND_BLEU        = "\u001B[44m";
    public static final String COULEUR_FOND_VIOLET      = "\u001B[45m";
    public static final String COULEUR_FOND_CYAN        = "\u001B[46m";
    public static final String COULEUR_FOND_BLANC       = "\u001B[47m";

    private final Scanner scanner;    // Entrée système.
    private final PrintStream output; // Sortie système pour les informations.
    private final PrintStream error;  // Sortie système pour les erreurs.

    public Ihm(InputStream input, PrintStream output, PrintStream error) {
        this.scanner = new Scanner(input);
        this.output = output;
        this.error = error;
    }

    public void afficherInformation(String message) {
        this.output.println(Ihm.COULEUR_CYAN + "[INFO] " + message + Ihm.COULEUR_REINITIALISATION);
    }

    public void afficherErreur(String erreur) {
        this.error.println(Ihm.COULEUR_ROUGE + "[ERREUR] " + erreur + Ihm.COULEUR_REINITIALISATION);
    }

    public String demanderString(String question) {
        // NOTE(nico): Pour éviter un overwrite de stdout
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            this.afficherErreur(e.getMessage());
        }

        this.output.println(Ihm.COULEUR_JAUNE + question + Ihm.COULEUR_REINITIALISATION);
        this.output.print(Ihm.COULEUR_JAUNE + "> " + Ihm.COULEUR_REINITIALISATION);
        return this.scanner.nextLine();
    }


    public int demanderInt(String question) {
        // NOTE(nico): Pour éviter un overwrite de stdout
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            this.afficherErreur(e.getMessage());
        }

        this.output.println(Ihm.COULEUR_JAUNE + question + Ihm.COULEUR_REINITIALISATION);
        this.output.print(Ihm.COULEUR_JAUNE + "> " + Ihm.COULEUR_REINITIALISATION);
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
