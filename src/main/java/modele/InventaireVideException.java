package modele;

/** Accès à un objet de l'inventaire quand l'inventaire est vide. */
public class InventaireVideException extends RuntimeException {
    public InventaireVideException(String message) {
        super(message);
    }
}
