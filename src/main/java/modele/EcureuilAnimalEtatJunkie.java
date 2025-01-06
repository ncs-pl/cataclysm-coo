package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class EcureuilAnimalEtatJunkie extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_ROUGE + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    //TODO(lucas) : Faut t-il le faire décorateur de rassasié ?

    private static EcureuilAnimalEtatJunkie instance; // Singleton
    private EcureuilAnimalEtatJunkie() { super(AnimalEtat.ETAT_JUNKIE); }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatJunkie.instance == null) EcureuilAnimalEtatJunkie.instance = new EcureuilAnimalEtatJunkie();
        return EcureuilAnimalEtatJunkie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        //TODO: Forcer l'animal à ne pas revenir sur ces pas ?

        int ligne;
        int colonne;

        for (int i = 0; i < 2; ++i){

            ligne   = animal.obtenirLigne();
            colonne = animal.obtenirColonne();

            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

            if (!zones.isEmpty()) {

                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }
        }

        ligne   = animal.obtenirLigne();
        colonne = animal.obtenirColonne();

        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            if (jeu.verifierCaseDecors(ligne, colonne)){
                int type = jeu.obtenirCaseDecors(ligne,colonne).obtenirType();
                if (type == Acteur.TYPE_ARBRE){
                    animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
                } else if (type == Acteur.TYPE_BUISSON){
                    animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
                } else {
                    animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
                }
            }
        } else {
            animal.changerSaturation(saturation - 1);
        }

        if (jeu.verifierCaseDecors(ligne, colonne)){
            int type = jeu.obtenirCaseDecors(ligne,colonne).obtenirType();
            if (type == Acteur.TYPE_ARBRE){
                animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatJunkie.obtenirInstance()));
            } else if (type == Acteur.TYPE_BUISSON){
                animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatJunkie.obtenirInstance()));
            }
        }
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override
    public String toString() {
        return EcureuilAnimalEtatJunkie.AFFICHAGE;
    }
}
