package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HibouEtatVol extends HibouEtat{

    private static HibouEtatVol instance;

    private HibouEtatVol() {
        super(HibouEtat.ETAT_VOL);
    }

    /** Obtient l'instance singleton de l'état. */
    public static HibouEtat obtenirInstance() {
        if (HibouEtatVol.instance == null) HibouEtatVol.instance = new HibouEtatVol();
        return HibouEtatVol.instance;
    }
    @Override
    public void deplacer(Hibou hibou, Jeu jeu) {
        int ligne   = hibou.obtenirLigne();
        int colonne = hibou.obtenirColonne();

        List<int[]> destinations = jeu.destinationsHibou(ligne,colonne);
        if(!(destinations.isEmpty())){
            Random rand = new Random();
            int[] dest = destinations.get(rand.nextInt(destinations.size()));
            hibou.changerColonne(dest[1]);
            hibou.changerLigne(dest[0]);
        }
        hibou.changerEtat(HibouEtatRepos.obtenirInstance());
    }
}
