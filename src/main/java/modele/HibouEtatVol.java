package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class HibouEtatVol extends HibouEtat {
    private static HibouEtatVol instance;
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_VIOLET + Hibou.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    // Singleton
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

        List<Animal> proies = jeu.chercherProieHibou(ligne,colonne);
        boolean aAttaque = false;

        if(!proies.isEmpty()) {
            for (Animal proie : proies) {
                if (!(proie.obtenirEtat() instanceof EcureuilAnimalEtatPerche) &&
                        !(proie.obtenirEtat() instanceof EcureuilAnimalEtatCache)) {
                    aAttaque = true;
                    hibou.changerLigne(proie.obtenirLigne());
                    hibou.changerColonne(proie.obtenirColonne());
                    System.out.println("aled");
                    System.out.println(proie.obtenirEtat());
                    if (!proie.fuire(jeu, List.of(Acteur.TYPE_ARBRE, Acteur.TYPE_BUISSON))){
                        jeu.obtenirAnimaux().remove(proie);
                    }
                    hibou.changerEtat(HibouEtatRepos.obtenirInstance());
                    break;
                }
            }
        }
        if (!aAttaque) {
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
        return AFFICHAGE; // TODO(nico): c.f. TODO dans Hibou.toString()
    }
}
