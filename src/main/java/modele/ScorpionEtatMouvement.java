package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

//TODO(Younes) : Décorateur pour l'état pacifique
//Logique fonctionnelle mais pas correcte en terme de conception
public class ScorpionEtatMouvement extends ScorpionEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_ROUGE + Ihm.COULEUR_BLANC + Scorpion.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static ScorpionEtatMouvement instance; // Singleton
    private ScorpionEtatMouvement() {
        super(SerpentEtat.ETAT_MOUVEMENT);
    }

    public static ScorpionEtatMouvement obtenirInstance() {
        if(ScorpionEtatMouvement.instance == null) ScorpionEtatMouvement.instance = new ScorpionEtatMouvement();
        return ScorpionEtatMouvement.instance;
    }

    public void deplacer(Scorpion scorpion , Jeu jeu) {
        int ligne   = scorpion.obtenirLigne();
        int colonne = scorpion.obtenirColonne();
        int maxPaix = scorpion.obtenirMaxPaix();
        int stadePaix = scorpion.obtenirStadePaix();

        List<Animal> proies = jeu.chercherProieVoisine(ligne, colonne);
        boolean aAttaque = false;

        for (Animal proie : proies) {
            if(stadePaix == maxPaix){
                aAttaque = true;
                int lProie = proie.obtenirLigne();
                int cProie = proie.obtenirColonne();
                jeu.obtenirAnimaux().remove(proie);
                scorpion.changerColonne(cProie);
                scorpion.changerLigne(lProie);
                scorpion.changerStadePaix(0);
                break;
            }
        }
        if (!aAttaque){
            if(stadePaix < maxPaix) scorpion.changerStadePaix(stadePaix + 1);
            List<ZoneVide> vides = jeu.chercherZonesVidesVoisine(ligne, colonne);
            List<Acteur> zones = jeu.obtenirRochersVoisins(ligne,colonne);
            zones.addAll(vides);
            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur zone = zones.get(rand.nextInt(zones.size()));
                scorpion.changerLigne(zone.obtenirLigne());
                scorpion.changerColonne(zone.obtenirColonne());
                if(zone.obtenirType() == Acteur.TYPE_PETIT_ROCHER) scorpion.changerEtat(ScorpionEtatCache.obtenirInstance());
            }
        }
    }

    @Override
    public String toString() {
        return ScorpionEtatMouvement.AFFICHAGE;
    }
}
