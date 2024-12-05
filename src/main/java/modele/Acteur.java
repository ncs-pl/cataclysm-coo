package modele;

/** Un acteur est une donnée représentant une entité dans le jeu (animal, objet, personnage, &c.). */
public abstract class Acteur {
    public final ActeurId id; // Nom identifiant le type d'objet.
    private int x;            // Position x de l'objet.
    private int y;            // Position y de l'objet.

    public Acteur(ActeurId id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
