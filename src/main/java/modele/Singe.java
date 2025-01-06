package modele;

import java.util.List;

/** Un singe. */
public class Singe extends Animal {
    public static final char SYMBOLE = 'S';

    public Singe(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SINGE, ligne, colonne, maxLigne, maxColonne, 2, 3);
        this.changerSaturation(3);
        this.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
    }

    @Override
    public void deplacer(Jeu jeu) {
        this.obtenirEtat().deplacer(this, jeu);
    }

    @Override
    public boolean fuire(Jeu jeu, List<Integer> type){
        return false;
    }

    @Override
    public String toString() {
        return this.obtenirEtat().toString();
    }
}
