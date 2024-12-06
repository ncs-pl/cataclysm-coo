package modele;

/** Un décor où peuvent se percher des écureuils. */
public class Arbre extends Acteur {
    public Arbre(int x, int y) {
        super(Acteur.TYPE_ARBRE, x, y);
    }
}