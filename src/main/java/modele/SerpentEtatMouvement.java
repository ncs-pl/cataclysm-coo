package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class SerpentEtatMouvement extends SerpentEtat{
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_NOIR + Serpent.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static SerpentEtatMouvement instance; // Singleton
    private SerpentEtatMouvement(){
        super(SerpentEtat.ETAT_MOUVEMENT);
    }

    public static SerpentEtatMouvement obtenirInstance(){
        if(SerpentEtatMouvement.instance == null) SerpentEtatMouvement.instance = new SerpentEtatMouvement();
        return SerpentEtatMouvement.instance;
    }

    public void deplacer(Serpent serpent , Jeu jeu){
        int ligne = serpent.obtenirLigne();
        int colonne = serpent.obtenirColonne();


        List<Animal> proies = jeu.chercherProieVoisine(ligne, colonne);
        boolean aAttaque = false;

        for (Animal proie : proies) {
            if(!(proie.obtenirEtat() instanceof SingeAnimalEtatPerche)){
                aAttaque = true;
                serpent.changerColonne(proie.obtenirColonne());
                serpent.changerLigne(proie.obtenirLigne());

                if (!proie.fuire(jeu, List.of(Acteur.TYPE_COCOTIER))) {
                    jeu.obtenirAnimaux().remove(proie);
                    serpent.changerEtat(SerpentEtatRepos.obtenirInstance());
                }
                break;
            }
        }
        if(!aAttaque){
            List<int[]> destinations = jeu.destinationsSerpent(ligne,colonne);
            if (!destinations.isEmpty()) {
                Random rand = new Random();
                int[] vide = destinations.get(rand.nextInt(destinations.size()));
                serpent.changerLigne(vide[0]);
                serpent.changerColonne(vide[1]);
            }
        }

    }

    @Override
    public String toString() {
        return SerpentEtatMouvement.AFFICHAGE;
    }
}
