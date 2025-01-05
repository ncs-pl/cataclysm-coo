package modele;

import vue.Ihm;

/** Un singe. */
public class Singe extends Animal {
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
    public String toString() {
        return this.obtenirEtat().toString();
    }
}
