package modele;

import vue.Ihm;

public class ChampignonHallucinogene extends Objet{
    public static final char SYMBOLE = 'T';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_BLEU + ChampignonHallucinogene.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public ChampignonHallucinogene(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_CHAMPIGNON_HALLUCINOGENE, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return ChampignonHallucinogene.AFFICHAGE;
    }
}
