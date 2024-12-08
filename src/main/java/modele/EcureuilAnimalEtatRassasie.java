package modele;

public class EcureuilAnimalEtatRassasie extends AnimalEtat {
    private EcureuilAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatRassasie.instance == null) {
            EcureuilAnimalEtatRassasie.instance = new EcureuilAnimalEtatRassasie();
        }

        return EcureuilAnimalEtatRassasie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();
        // On cherche la première cellule vide parmi celle du dessus,
        // de gauche, de droite, ou d'en dessous (dans cet ordre), ou sinon
        // on ne bouge pas.
             if (jeu.verifierCaseVide(ligne - 1, colonne))     ligne   -= 1;
        else if (jeu.verifierCaseVide(ligne,     colonne - 1)) colonne -= 1;
        else if (jeu.verifierCaseVide(ligne,     colonne + 1)) colonne += 1;
        else if (jeu.verifierCaseVide(ligne + 1, colonne))     ligne   += 1;
        animal.changerLigne(ligne);
        animal.changerColonne(colonne);

        int saturation = animal.obtenirSaturation();
        animal.changerSaturation(saturation - 1);

        if (saturation == 0) {
            animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }
}
