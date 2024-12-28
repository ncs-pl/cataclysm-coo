package modele;

public class Renard extends Animal {
    public Renard(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_RENARD, ligne, colonne, maxLigne, maxColonne, 1, 5);
        this.changerSaturation(0);
        this.changerEtat(RenardEtatAffame.obtenirInstance());
    }

    @Override
    public void deplacer(Jeu jeu) {}

    @Override
    public String toString() {
        return "";
    }
}
