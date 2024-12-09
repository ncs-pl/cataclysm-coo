package modele;

/** Un gland pouvant nourrir les écureuils. */
public class Gland extends Objet {
    public Gland(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_GLAND, ligne, colonne, maxLigne, maxColonne);
    }
}
