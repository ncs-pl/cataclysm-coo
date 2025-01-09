package modele;

public abstract class Predateur extends Acteur {

    private int sante;

    public Predateur(int type, int ligne, int colonne, int maxLigne, int maxColonne) {
        super(type, ligne, colonne, maxLigne, maxColonne);
        this.sante = 100;
    }

    public Predateur(int type, int ligne, int colonne, int maxLigne, int maxColonne,int sante) {
        super(type, ligne, colonne, maxLigne, maxColonne);
        this.sante = sante;
    }

    public int obtenirSante() {
        return sante;
    }

    public void changerSante(int sante) {
        this.sante = sante;
    }

    abstract public void deplacer(Jeu jeu);
}
