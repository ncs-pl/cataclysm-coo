package modele;

/** Un décor où peuvent se percher des écureuils. */
public class Arbre extends Acteur {
    public Arbre(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_ARBRE, ligne, colonne, maxLigne, maxColonne);
    }
}