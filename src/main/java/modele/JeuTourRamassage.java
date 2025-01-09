package modele;

public class JeuTourRamassage extends JeuTour {
    private Position position; // La position relative de l'objet à ramasser.

    public JeuTourRamassage() {
        super();
    }

    /** Obtient une copie de la position relative dans le tour. */
    public Position obtenirPosition() {
        return MEMCPY.MEMCPY_POSITION(this.position);
    }

    /** Change la position relative jouée dans le tour par une copie de la position donnée. */
    public void changerPosition(Position position) {
        this.position = MEMCPY.MEMCPY_POSITION(position);
    }
}
