package modele;

public class JeuTourDepot extends JeuTour {
    private Position position; // La position relative où déposer l'objet.
    private int objet;         // Indice dans l'inventaire de l'objet à déposer.

    /** Obtient une copie de la position relative dans le tour. */
    public Position obtenirPosition() {
        return MEMCPY.MEMCPY_POSITION(this.position);
    }

    /** Change la position relative jouée dans le tour par une copie de la position donnée. */
    public void changerPosition(Position position) {
        this.position = MEMCPY.MEMCPY_POSITION(position);
    }

    /** Obtient une copie de l'indice de l'objet à déposer. */
    public int obtenirObjet() {
        return this.objet; // Les entiers sont passés par valeur
    }

    /** Change l'objet à déposer. */
    public void changerObjet(int objet) {
        this.objet = objet;
    }
}
