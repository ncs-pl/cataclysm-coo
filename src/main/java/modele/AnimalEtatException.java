package modele;

/** Erreur de logique en appelant une transition invalide dans un état. */
public class AnimalEtatException extends RuntimeException {
    public AnimalEtatException(String message) {
        super(message);
    }
}
