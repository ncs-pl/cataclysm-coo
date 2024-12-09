package modele;

/** Un décor où peuvent se cacher les singes. */
public class PetitRocher extends Acteur {
    public PetitRocher(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_PETIT_ROCHER, ligne, colonne, maxLigne, maxColonne);
    }
}
