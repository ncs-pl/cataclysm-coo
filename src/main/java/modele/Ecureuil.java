package modele;

import vue.Ihm;

/** Un animal dans la forêt. */
public class Ecureuil extends Animal {
    public Ecureuil(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_ECUREUIL, ligne, colonne, maxLigne, maxColonne, 1, 5);
        this.changerSaturation(0);
        this.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
    }

    @Override
    public void deplacer(Jeu jeu) {
        this.obtenirEtat().deplacer(this, jeu);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE      +
                Ihm.COULEUR_BLEU            +
                Acteur.SYMBOLE_ECUREUIL      +
                Ihm.COULEUR_REINITIALISATION;
    }
}