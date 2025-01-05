package modele;

public class Scorpion extends Predateur{

    private ScorpionEtat etat = ScorpionEtatMouvement.obtenirInstance();

    public Scorpion(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SCORPION, ligne, colonne, maxLigne, maxColonne);
    }

    public ScorpionEtat obtenirEtat() {
        return etat;
    }

    public void changerEtat(ScorpionEtat etat) {
        this.etat = etat;
    }

    @Override
    public void deplacer(Jeu jeu) {
        etat.deplacer(this,jeu);
    }
}
