package modele;

import java.util.List;
import java.util.Random;

public class SerpentEtatMouvement extends SerpentEtat{
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

        Animal proie = jeu.chercherProieVoisine(ligne,colonne);
        if(proie != null){
            int lProie = proie.obtenirLigne();
            int cProie = proie.obtenirColonne();
            jeu.obtenirAnimaux().remove(proie);
            serpent.changerColonne(cProie);
            serpent.changerLigne(lProie);
            serpent.changerEtat(SerpentEtatRepos.obtenirInstance());
        }
        else{
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
        return ""; // TODO(nico): c.f. Serpent.toString()
    }
}
