package modele;

public class Hibou extends Predateur{
    public Hibou(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_HIBOU, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public void deplacer(Jeu jeu) {}
}
