package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class Renard extends Predateur {
    public static final char SYMBOLE = 'R';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR + Renard.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public Renard(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_RENARD, ligne, colonne, maxLigne, maxColonne);
    }
    public Renard(int ligne, int colonne, int maxLigne, int maxColonne,int sante) {
        super(Acteur.TYPE_RENARD, ligne, colonne, maxLigne, maxColonne,sante);
    }

    @Override
    public void deplacer(Jeu jeu) {
        int ligne   = this.obtenirLigne();
        int colonne = this.obtenirColonne();

        List<Animal> proies = jeu.chercherProieVoisine(ligne,colonne);
        boolean aManger = false;

        if(!proies.isEmpty()) {
            for(Animal proie : proies) {
                if (!(proie.obtenirEtat() instanceof EcureuilAnimalEtatPerche)) {
                    aManger = true;
                    this.changerColonne(proie.obtenirColonne());
                    this.changerLigne(proie.obtenirLigne());
                    jeu.obtenirAnimaux().remove(proie);
                }
            }
        }
        if (!aManger) {
            List<ZoneVide> vides = jeu.chercherZonesVidesVoisine(ligne, colonne);
            if (!vides.isEmpty()) {
                Random rand = new Random();
                Acteur vide = vides.get(rand.nextInt(vides.size()));
                this.changerLigne(vide.obtenirLigne());
                this.changerColonne(vide.obtenirColonne());
            }
        }
    }

    @Override
    public String toString() {
        return Renard.AFFICHAGE; // TODO(nico): toString sur les Etats ?
    }
}
