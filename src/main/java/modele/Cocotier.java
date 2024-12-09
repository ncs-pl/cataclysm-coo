package modele;

/** Un décor sur lequel peut se percher un singe. */
public class Cocotier extends Acteur {
    public Cocotier(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_COCOTIER, ligne, colonne, maxLigne, maxColonne);
    }
}
