package modele;

/** Erreur pour une commande du jeu ayant une position invalide. */
public class PositionInvalideException extends RuntimeException {
    public PositionInvalideException(String message) {
        super(message);
    }
}
