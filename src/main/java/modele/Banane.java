package modele;

/** Une banane pour nourrir un singe. */
public class Banane extends Objet {
    public Banane(int ligne, int colonne, int maxLigne, int maxColonne) {
      super(Acteur.TYPE_BANANE, ligne, colonne, maxLigne, maxColonne);
    }
}
