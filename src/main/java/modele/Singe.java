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
    public boolean fuire(Jeu jeu, List<Integer> types){
        List<Acteur> decors = jeu.chercherDecorsVoisinsVide(this.obtenirLigne(), this.obtenirColonne());
        for (Acteur decor : decors) {
            if (types.contains(decor.obtenirType())){
                this.changerLigne(decor.obtenirLigne());
                this.changerColonne(decor.obtenirColonne());
                if (decor.obtenirType() == Acteur.TYPE_COCOTIER){
                    this.changerEtat(new SingeAnimalEtatPerche(SingeAnimalEtatEffraye.obtenirInstance()));
                } else if (decor.obtenirType() == Acteur.TYPE_PETIT_ROCHER){
                    this.changerEtat(new SingeAnimalEtatCache(SingeAnimalEtatEffraye.obtenirInstance()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.obtenirEtat().toString();
    }
}
