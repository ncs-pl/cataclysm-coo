package modele;

public class RenardEtatAffame extends AnimalEtat {
    private static RenardEtatAffame instance;

    private RenardEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (RenardEtatAffame.instance == null) RenardEtatAffame.instance = new RenardEtatAffame();
        return RenardEtatAffame.instance;
    }
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
    }

    @Override
    public void prendreCoup(Animal animal) {
    }

    @Override
    public String toString() {
        return ""; // TODO(nico): c.f. TODO sur Renard.toString()
    }
}
