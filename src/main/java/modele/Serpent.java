package modele;

public class Serpent extends Predateur{

    private SerpentEtat etat = SerpentEtatMouvement.obtenirInstance();

    public Serpent(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SERPENT, ligne, colonne, maxLigne, maxColonne);
    }

    public SerpentEtat obtenirEtat() {
        return etat;
    }

    public void changerEtat(SerpentEtat etat) {
        this.etat = etat;
    }


    @Override
    public void deplacer(Jeu jeu) {
        etat.deplacer(this,jeu);
    }
}
