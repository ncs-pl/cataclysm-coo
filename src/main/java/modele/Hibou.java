package modele;

public class Hibou extends Animal{
    public Hibou(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_HIBOU, ligne, colonne, maxLigne, maxColonne, 1, 5);
        this.changerSaturation(0);
        this.changerEtat(HibouEtatAffame.obtenirInstance());
    }

    @Override
    public void deplacer(Jeu jeu) {}
}
