package modele;

public class Renard extends Predateur {
    public Renard(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_RENARD, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public void deplacer(Jeu jeu) {}
}
