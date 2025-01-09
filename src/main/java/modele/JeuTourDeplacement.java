package modele;

public class JeuTourDeplacement extends JeuTour {
    private Position position; // La position relative de déplacement

    public JeuTourDeplacement() {
        super();
    }

    /** Obtient une copie de la position relative jouée dans le tour. */
    public Position obtenirPosition() {
        return MEMCPY.MEMCPY_POSITION(this.position);
    }

    /** Change la position relative jouée dans le tour par une copie de la position donnée. */
    public void changerPosition(Position position) {
        this.position = MEMCPY.MEMCPY_POSITION(position);
    }
}
