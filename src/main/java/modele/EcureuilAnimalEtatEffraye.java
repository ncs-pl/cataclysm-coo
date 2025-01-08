package modele;

import vue.Ihm;

import java.util.HashMap;
import java.util.Map;

public class EcureuilAnimalEtatEffraye extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    private Map<Animal,Integer> tourEffrayer;

    private static EcureuilAnimalEtatEffraye instance;
    private EcureuilAnimalEtatEffraye() {
        super(AnimalEtat.ETAT_EFFRAYE);
        tourEffrayer = new HashMap<Animal,Integer>();
    }

    public static AnimalEtat obtenirInstance(){
        if (EcureuilAnimalEtatEffraye.instance == null) EcureuilAnimalEtatEffraye.instance = new EcureuilAnimalEtatEffraye();
        return EcureuilAnimalEtatEffraye.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu){
        int ligne = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        if(tourEffrayer.containsKey(animal)){
            tourEffrayer.put(animal, tourEffrayer.get(animal) - 1);
            if (tourEffrayer.get(animal) <= 0){
                System.out.println("aled");
                tourEffrayer.remove(animal);
                if(animal.obtenirSaturation() == 0){
                    if (jeu.verifierCaseDecors(ligne, colonne)){
                        int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
                        if(type == Acteur.TYPE_ARBRE){
                            animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
                        } else if (type == Acteur.TYPE_BUISSON) {
                            animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
                        } else {
                            animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
                        }
                    } else {
                        animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
                    }
                } else {
                    animal.changerSaturation(animal.obtenirSaturation() - 1);
                }
            }
        } else {
            tourEffrayer.put(animal, 2);
            if (jeu.verifierCaseDecors(ligne, colonne)) {
                int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
                if(type == Acteur.TYPE_ARBRE){
                    animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatEffraye.obtenirInstance()));
                } else if (type == Acteur.TYPE_BUISSON){
                    animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatEffraye.obtenirInstance()));
                }
            }
        }
    }

    @Override
    public void prendreCoup(Animal animal){
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString(){
        return AFFICHAGE;
    }
}
