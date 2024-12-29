package modele;

import java.util.List;
import java.util.Random;

public class Renard extends Predateur {
    public Renard(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_RENARD, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public void deplacer(Jeu jeu) {
        int ligne   = this.obtenirLigne();
        int colonne = this.obtenirColonne();

        List<ZoneVide> vides = jeu.chercherZonesVidesVoisine(ligne, colonne);
        if (!vides.isEmpty()) {
            Random rand = new Random();
            Acteur vide = vides.get(rand.nextInt(vides.size()));
            this.changerLigne(vide.obtenirLigne());
            this.changerColonne(vide.obtenirColonne());
        }
    }
}
