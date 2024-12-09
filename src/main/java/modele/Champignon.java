package modele;

/** Un objet pouvant nourrir des animaux pour les rendre junkies. */
public class Champignon extends Objet {
    public Champignon(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_CHAMPIGNON, ligne, colonne, maxLigne, maxColonne);
    }
}
