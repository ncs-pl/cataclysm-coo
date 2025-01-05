package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class EcureuilAnimalEtatRassasie extends AnimalEtat {
    private static EcureuilAnimalEtatRassasie instance; // Singleton
    private EcureuilAnimalEtatRassasie() { super(AnimalEtat.ETAT_RASSASIE); }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatRassasie.instance == null) EcureuilAnimalEtatRassasie.instance = new EcureuilAnimalEtatRassasie();
        return EcureuilAnimalEtatRassasie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
        zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

        int decors = -1;

        if (!zones.isEmpty()) {
            Random rand = new Random();
            Acteur next = zones.get(rand.nextInt(zones.size()));

            if (next.obtenirType() == Acteur.TYPE_ARBRE || next.obtenirType() == Acteur.TYPE_BUISSON) {
                decors = next.obtenirType();
            }

            animal.changerLigne(next.obtenirLigne());
            animal.changerColonne(next.obtenirColonne());
        }


        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            if(decors == Acteur.TYPE_ARBRE)
                animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
            else if (decors == Acteur.TYPE_BUISSON)
                animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
            else
                animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
        } else {
            animal.changerSaturation(saturation - 1);
        }

        if(decors == Acteur.TYPE_ARBRE)
            animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatRassasie.obtenirInstance()));
        else if (decors == Acteur.TYPE_BUISSON)
            animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatRassasie.obtenirInstance()));
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_BLEU + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
