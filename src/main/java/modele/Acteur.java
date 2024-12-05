package modele;

/** Un acteur est une donnée représentant une entité dans le jeu (animal, objet, personnage, &c.). */
public abstract class Acteur {
    public final ActeurId id; // Nom identifiant le type d'objet.
    private int colonne;      // Colonne de la position de l'objet.
    private int ligne;        // Ligne de la position de l'objet.

    public Acteur(ActeurId id, int colonne, int ligne) {
        this.id = id;
        this.colonne = colonne;
        this.ligne = ligne;
    }

    public int getColonne() {
        return this.colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public int getLigne() {
        return this.ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
}
