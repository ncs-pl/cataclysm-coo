package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class HibouEtatVol extends HibouEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_VIOLET + Hibou.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static HibouEtatVol instance; // Singleton
    private HibouEtatVol() { super(HibouEtat.ETAT_VOL); }

    /** Obtient l'instance singleton de l'état. */
    public static HibouEtat obtenirInstance() {
        if (HibouEtatVol.instance == null) HibouEtatVol.instance = new HibouEtatVol();
        return HibouEtatVol.instance;
    }
    @Override
    public void deplacer(Hibou hibou, Jeu jeu) {
        int ligne   = hibou.obtenirLigne();
        int colonne = hibou.obtenirColonne();

        Animal proie = jeu.chercherProieHibou(ligne,colonne);
        if(proie != null){
            int lProie = proie.obtenirLigne();
            int cProie = proie.obtenirColonne();
            jeu.obtenirAnimaux().remove(proie);
            hibou.changerColonne(cProie);
            hibou.changerLigne(lProie);
            hibou.changerEtat(HibouEtatRepos.obtenirInstance());
        }

        else{
            List<int[]> destinations = jeu.destinationsHibou(ligne,colonne);
            if(!(destinations.isEmpty())){
                Random rand = new Random();
                int[] dest = destinations.get(rand.nextInt(destinations.size()));
                hibou.changerColonne(dest[1]);
                hibou.changerLigne(dest[0]);
            }
        }
    }

    @Override
    public String toString() {
        return HibouEtatVol.AFFICHAGE;
    }
}
