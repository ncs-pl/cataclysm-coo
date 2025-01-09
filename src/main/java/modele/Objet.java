package modele;

/** Un objet est un acteur pouvant être récupéré pa le joueur. */
public abstract class Objet extends Acteur {
    public Objet(int type, int ligne, int colonne, int maxLigne, int maxColonne) {
        super(type, ligne, colonne, maxLigne, maxColonne);
    }
}
