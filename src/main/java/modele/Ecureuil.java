package modele;

import java.util.List;

/** Un animal dans la forêt. */
public class Ecureuil extends Animal {
    public static final char SYMBOLE = 'E';

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
    public boolean fuire(Jeu jeu, List<Integer> types){
        List<Acteur> decors = jeu.chercherDecorsVoisinsVide(this.obtenirLigne(), this.obtenirColonne());
        for (Acteur decor : decors) {
            if (types.contains(decor.obtenirType())){
                this.changerLigne(decor.obtenirLigne());
                this.changerColonne(decor.obtenirColonne());
                if (decor.obtenirType() == Acteur.TYPE_ARBRE){
                    this.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatEffraye.obtenirInstance()));
                } else if (decor.obtenirType() == Acteur.TYPE_BUISSON){
                    this.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatEffraye.obtenirInstance()));
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
