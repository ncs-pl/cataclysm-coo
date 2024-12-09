package modele;

/** Un décor où peuvent de cacher les écureuils. */
public class Buisson extends Acteur {
    public Buisson(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_BUISSON, ligne, colonne, maxLigne, maxColonne);
    }
}
