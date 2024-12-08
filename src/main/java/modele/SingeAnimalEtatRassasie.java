package modele;

public class SingeAnimalEtatRassasie extends AnimalEtat {
    private SingeAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatRassasie.instance == null) {
            SingeAnimalEtatRassasie.instance = new SingeAnimalEtatRassasie();
        }

        return SingeAnimalEtatRassasie.instance;
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
            animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
        }
    }

    @Override public void manger(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Singe déjà rassasié...");
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }
}
