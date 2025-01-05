package modele;

public abstract class Predateur extends Acteur {
    public Predateur(int type, int ligne, int colonne, int maxLigne, int maxColonne) {
        super(type, ligne, colonne, maxLigne, maxColonne);
    }

    abstract public void deplacer(Jeu jeu);
}
